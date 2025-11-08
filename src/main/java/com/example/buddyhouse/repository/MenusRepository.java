package com.example.buddyhouse.repository;

import com.example.buddyhouse.entity.MenusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenusRepository extends JpaRepository<MenusEntity,Long> {

}
