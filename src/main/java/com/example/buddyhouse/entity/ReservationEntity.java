package com.example.buddyhouse.entity;

import com.example.buddyhouse.enums.ReservationStatus;
import com.example.buddyhouse.enums.ReservationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="reservation")
@NamedEntityGraph(
    name = "Reservation.withDetails",
    attributeNodes = {
        @NamedAttributeNode("frame"),
        @NamedAttributeNode("customer"),
        @NamedAttributeNode("menu"),
        @NamedAttributeNode(value =  "reservationPets", subgraph = "reservationPetsSubgraph")
    },
    subgraphs = {
        @NamedSubgraph(
            name = "reservationPetsSubgraph",
            attributeNodes = {
                @NamedAttributeNode("pets")
            }

    )
    }
)



public class ReservationEntity {

  /** 予約ID*/
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 使用している予約枠ID(外部キー)
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reservation_frame_id")
  private ReservationFrameEntity frame;
  /**
   * 選択中のメニューID（外部キー）
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_id")
  private MenuEntity menu;

  /**
   * 予約している顧客ID（外部キー）
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private  CustomerEntity customer;

  @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
  @Builder.Default
  private List<ReservationPetEntity> reservationPets = new ArrayList<>();

  /**予約のスタート時間*/
  @Column(name = "start_at", nullable = false)
  private LocalDateTime startAt;

  /**予約の終了時間*/
  @Column(name = "end_at", nullable = false)
  private LocalDateTime endAt;

  /**予約のステータス*/
  @Enumerated(EnumType.STRING)//(RESERVED,CHECK_IN,CHECK_OUT,CANCELLED)
  @Column(name = "status", nullable = false)
  private ReservationStatus status;

  /** 削除フラグ */
  @Column(nullable = false)
  @Builder.Default
  private boolean deleted = false;

  /** 登録日時 */
  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  /** 更新日時 */
  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;


  public void cancel() {
    if (this.status==ReservationStatus.CANCELLED) {
      throw new IllegalStateException("すでにキャンセル済みの予約です。");
    }
   this.status=ReservationStatus.CANCELLED;
  }

public static ReservationEntity create(ReservationFrameEntity frame,
      UserEntity user,
      MenuEntity menu,
      List<PetsEntity> pets){

    if (menu.getReservationType() != ReservationType.DAYCARE){
      throw new IllegalArgumentException("メニューと予約枠のタイプが違います");
    }
    frame.addUsedDogs(pets.size());

    return ReservationEntity.builder()
        .frame(frame)
        .customer(user.getCustomer())
        .menu(menu)
        .startAt(frame.getStartAt())
        .endAt(frame.getEndAt())
        .status(ReservationStatus.RESERVED)
        .build();




  }

}
