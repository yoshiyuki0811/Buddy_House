package com.example.buddyhouse.repository;



import com.example.buddyhouse.entity.PetsEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetsRepository extends JpaRepository<PetsEntity,Long> {
List<PetsEntity> findAllByCustomerIdAndDeletedFalse(Long customerId);
}
