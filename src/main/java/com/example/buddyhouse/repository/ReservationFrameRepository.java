package com.example.buddyhouse.repository;


import com.example.buddyhouse.entity.ReservationFrameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationFrameRepository extends JpaRepository<ReservationFrameEntity,Long> {

}
