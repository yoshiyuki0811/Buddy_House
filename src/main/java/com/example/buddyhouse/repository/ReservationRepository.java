package com.example.buddyhouse.repository;

import com.example.buddyhouse.entity.ReservationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {
  //予約一覧用
  @EntityGraph(value = "Reservation.withDetails", type = EntityGraph.EntityGraphType.LOAD)
  List<ReservationEntity> findAll();
}
