package com.example.buddyhouse.repository;

import com.example.buddyhouse.entity.MenuEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenusRepository extends JpaRepository<MenuEntity,Long> {
  List<MenuEntity> findByActiveTrueAndDeletedFalse();
}
