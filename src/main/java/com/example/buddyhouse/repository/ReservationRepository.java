package com.example.buddyhouse.repository;

import com.example.buddyhouse.entity.ReservationEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {
  //予約一覧用
  @EntityGraph(value = "Reservation.withDetails", type = EntityGraph.EntityGraphType.LOAD)
  List<ReservationEntity> findAll();

  //予約詳細取得用
  @EntityGraph(value = "Reservation.withDetails",type = EntityGraphType.LOAD)
  Optional<ReservationEntity> findById(Long id);

  //日付別検索
  @EntityGraph(value = "Reservation.withDetails",type = EntityGraphType.LOAD)
  List<ReservationEntity> findByStartAtBetween(
      LocalDateTime start, LocalDateTime end
  );
  @EntityGraph(value ="Reservation.withDetails",type = EntityGraphType.LOAD )
  List<ReservationEntity> findByCustomerIdOrderByStartAtDesc(@Param("customerId") Long customersId);




}


