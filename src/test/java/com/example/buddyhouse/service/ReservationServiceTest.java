package com.example.buddyhouse.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.buddyhouse.dto.reservation.ReservationCancelDto;
import com.example.buddyhouse.dto.reservation.ReservationDto;
import com.example.buddyhouse.dto.reservation.ReservationRequestDto;
import com.example.buddyhouse.entity.CustomerEntity;
import com.example.buddyhouse.entity.MenuEntity;
import com.example.buddyhouse.entity.PetsEntity;
import com.example.buddyhouse.entity.ReservationEntity;
import com.example.buddyhouse.entity.ReservationFrameEntity;
import com.example.buddyhouse.entity.ReservationPetEntity;
import com.example.buddyhouse.entity.UserEntity;
import com.example.buddyhouse.enums.ReservationStatus;
import com.example.buddyhouse.enums.ReservationType;
import com.example.buddyhouse.mapper.ReservationMapper;
import com.example.buddyhouse.repository.MenusRepository;
import com.example.buddyhouse.repository.PetsRepository;
import com.example.buddyhouse.repository.ReservationFrameRepository;
import com.example.buddyhouse.repository.ReservationRepository;
import com.example.buddyhouse.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;


@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
  @Mock
  private ReservationRepository reservationRepository;

  @Mock
  private ReservationMapper reservationMapper;

  @Mock private ReservationFrameRepository reservationFrameRepository;
  @Mock private UserRepository userRepository;
  @Mock private MenusRepository menusRepository;
  @Mock private PetsRepository petsRepository;

  @InjectMocks
  private ReservationService reservationService;

  Long reservationId = 1L;
  ReservationPetEntity rp1 = ReservationPetEntity.builder().build();
  ReservationPetEntity rp2 = ReservationPetEntity.builder().build();
  MenuEntity  menu= MenuEntity.builder().build();
  ReservationRequestDto requestDto = ReservationRequestDto.builder().build();

  @Test
  @DisplayName("予約をキャンセルした場合、予約枠の使用頭数が減る")
  void cancel_shouldDecreaseUsedDogs(){

    //Arrange(準備)

    ReservationFrameEntity frame = ReservationFrameEntity.builder()
        .maxDogs(10)
        .usedDogs(5)
        .build();


    ReservationEntity reservation = ReservationEntity.builder()
        .id(reservationId)
        .status(ReservationStatus.RESERVED)
        .frame(frame)
        .reservationPets(List.of(rp1,rp2))
        .build();

    when(reservationRepository.findById(reservationId))
        .thenReturn(Optional.of(reservation));

    // save
    when(reservationRepository.save(any(ReservationEntity.class)))
        .thenAnswer(inv -> inv.getArgument(0));

    // mapper
    ReservationCancelDto dummyDto = mock(ReservationCancelDto.class);
    when(reservationMapper.toCancelDto(any(ReservationEntity.class)))
        .thenReturn(dummyDto);

    // Act
    ReservationCancelDto result = reservationService.cancelReservation(reservationId);

    // Assert
    assertEquals(3, frame.getUsedDogs());
    assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
    assertSame (dummyDto, result);

    verify(reservationRepository).save(reservation);
    verify(reservationMapper).toCancelDto(reservation);

  }

  @Test
  @DisplayName("すでにキャンセル済みの予約を再度キャンセルすると例外が発生する")
  void cancel_alreadyCancelled_shouldThrowException() {

    // Arrange

    ReservationFrameEntity frame = ReservationFrameEntity.builder()
        .id(1L)
        .maxDogs(10)
        .usedDogs(5)
        .build();


    ReservationEntity reservation = ReservationEntity.builder()
        .id(reservationId)
        .status(ReservationStatus.CANCELLED) // ← すでにキャンセル済み
        .frame(frame)
        .reservationPets(List.of(
            ReservationPetEntity.builder().build()
        ))
        .build();

    when(reservationRepository.findById(reservationId))
        .thenReturn(Optional.of(reservation));

    IllegalStateException exception =
        assertThrows(
            IllegalStateException.class,
            () -> reservationService.cancelReservation(reservationId)
        );

    assertEquals(
        "すでにキャンセルされています。 id=" + reservationId,
        exception.getMessage()
    );
    verify(reservationRepository, never()).save(any());
  }

  @Test
  @DisplayName("予約作成：予約した頭数分だけ予約枠のusedDogsが増える（2頭）")
  void createReservation_incrementsUsedDogs_byPetCount() {
    // -------- Arrange --------
    // SecurityContext（ログイン中ユーザーの email）
    String email = "test@example.com";
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(email, null, List.of())
    );

    ReservationFrameEntity frame = ReservationFrameEntity.builder()
        .id(1L)
        .maxDogs(10)
        .usedDogs(5)
        .build();

    // メニューの作成
    MenuEntity menu = MenuEntity.builder()
        .id(10L)
        .reservationType(ReservationType.DAYCARE)
        .build();

    // ユーザーの作成
    CustomerEntity customer = CustomerEntity.builder().id(1000L).build();
    UserEntity user = UserEntity.builder()
        .id(100L)
        .email(email)
        .customer(customer)
        .build();

    // ペットの作成
    PetsEntity pet1 = PetsEntity.builder().id(100L).build();
    PetsEntity pet2 = PetsEntity.builder().id(101L).build();

    ReservationRequestDto requestDto = ReservationRequestDto.builder()
        .reservationFrameId(1L)
        .menuId(10L)
        .petIds(List.of(100L, 101L))
        .build();

    // Repositoryの振る舞い
    when(reservationFrameRepository.findById(1L)).thenReturn(Optional.of(frame));
    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    when(menusRepository.findById(10L)).thenReturn(Optional.of(menu));
    when(petsRepository.findAllById(List.of(100L, 101L))).thenReturn(List.of(pet1, pet2));

    // saveは引数をそのまま返す
    when(reservationRepository.save(any(ReservationEntity.class)))
        .thenAnswer(inv -> inv.getArgument(0));

    // mapper
    ReservationDto dummyDto = mock(ReservationDto.class);
    when(reservationMapper.toDto(any(ReservationEntity.class))).thenReturn(dummyDto);

    // Act
    ReservationDto result = reservationService.createReservation(requestDto);

    // Assert
    // saveされたReservationを捕まえて中身を検証
    ArgumentCaptor<ReservationEntity> captor = ArgumentCaptor.forClass(ReservationEntity.class);
    verify(reservationRepository, times(1)).save(captor.capture());

    ReservationEntity saved = captor.getValue();

    // usedDogsが 5 + 2 = 7 になっているか
    assertEquals(7, saved.getFrame().getUsedDogs());

    // 中間テーブル（reservationPets）が2件作られているか
    assertEquals(2, saved.getReservationPets().size());

    // mapper結果が返っているか（最低限）
    assertSame(dummyDto, result);

    // 後片付け（他テストへの汚染防止）
    SecurityContextHolder.clearContext();
  }
}