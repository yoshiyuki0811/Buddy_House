package com.example.buddyhouse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reservation_pet")
public class ReservationPetEntity {

  /**
   * ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  /**
   * ペットID
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "pets_id")
  private PetsEntity pets;

  /**
   * 予約ID
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reservation_id")
  private ReservationEntity reservation;

  /**
   * ペットごとのメニュー（任意）
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_id")
  private MenuEntity menu;


  /**
   * 削除フラグ
   */
  @Column(nullable = false)
  @Builder.Default
  private boolean deleted = false;

  /**
   * 登録日時
   */
  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  /**
   * 更新日時
   */
  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;


}
