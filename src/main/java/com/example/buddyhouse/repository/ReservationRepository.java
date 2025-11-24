package com.example.buddyhouse.repository;

import com.example.buddyhouse.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {

}
