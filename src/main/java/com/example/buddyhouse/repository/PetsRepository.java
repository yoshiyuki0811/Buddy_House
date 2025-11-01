package com.example.buddyhouse.repository;


import com.example.buddyhouse.entity.PetsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetsRepository extends JpaRepository<PetsEntity,Long> {

}
