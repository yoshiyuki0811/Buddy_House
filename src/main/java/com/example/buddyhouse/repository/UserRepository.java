package com.example.buddyhouse.repository;

import com.example.buddyhouse.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity , Long> {
  Optional<UserEntity> findByEmail(String email);

}
