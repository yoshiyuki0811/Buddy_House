package com.example.buddyhouse.repository;

import com.example.buddyhouse.dto.CustomersListDto;
import com.example.buddyhouse.entity.CustomerEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
//顧客情報一覧取得用
  @Query("SELECT new com.example.buddyhouse.dto.CustomersListDto(c.id, c.name, c.phone)"+ "FROM CustomerEntity c")
  List<CustomersListDto> findAllForList();
}
