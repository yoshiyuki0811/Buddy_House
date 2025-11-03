package com.example.buddyhouse.repository;


import com.example.buddyhouse.dto.PetsListDto;
import com.example.buddyhouse.entity.PetsEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetsRepository extends JpaRepository<PetsEntity,Long> {
  @Query("SELECT new com.example.buddyhouse.dto.PetsListDto(p.id, p.customer.id, p.name, p.breed, p.weight )"+ "FROM PetsEntity p")
  List<PetsListDto> findAllPetsList();
}
