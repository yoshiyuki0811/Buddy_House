package com.example.buddyhouse.repository;

import com.example.buddyhouse.entity.ReservationEntity;
import com.example.buddyhouse.entity.ReservationFrameEntity;
import com.example.buddyhouse.entity.ReservationPetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationPetRepository extends JpaRepository<ReservationPetEntity,Long> {

}
