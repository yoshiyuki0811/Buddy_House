package com.example.buddyhouse.repository;

import com.example.buddyhouse.entity.ReservationEntity;
import com.example.buddyhouse.entity.ReservationFrameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {

}
