package com.example.buddyhouse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * 顧客データ
 */

@Setter
@Getter
@Entity
@Table(name = "customers")
public class CustomerEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; //顧客ID（自動採番）

  @Column(nullable = false,length = 100)
  private String name; //顧客名

  @Column(nullable = false)
  private String address; //住所

  @Column(nullable = false, unique = true)
  private String email; //メールアドレス

  @Column(nullable = false, length = 20)
  private String phone; //電話番号

  @Column(insertable = false, updatable = false)
  private LocalDateTime createdAt; //登録日時(自動更新)

  @Column(insertable = false)
  private LocalDateTime updatedAt; //更新日時（自動更新）


}
