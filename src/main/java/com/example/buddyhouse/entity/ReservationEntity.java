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
@Table(name ="reservation")
public class ReservationEntity {

  /** 予約ID*/
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 使用している予約枠ID(外部キー)
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reservation_frame_id")
  private ReservationFrameEntity reservationFrame;
  /**
   * 選択中のメニューID（外部キー）
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_id")
  private MenuEntity menu;

  /**
   * 予約している顧客ID（外部キー）
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private  CustomerEntity customer;

  /** 来店犬数*/
  @Column(name = "num_dogs", nullable = false)
  private Long numDogs;

  /**予約のスタート時間*/
  @Column(name = "start_at", nullable = false)
  private LocalDateTime startAt;

  /**予約の終了時間*/
  @Column(name = "end_at", nullable = false)
  private LocalDateTime endAt;

  /** 削除フラグ */
  @Column(nullable = false)
  @Builder.Default
  private boolean deleted = false;

  /** 登録日時 */
  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  /** 更新日時 */
  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  public void delete() {
    if (this.deleted) {
      throw new IllegalStateException("すでに削除済みの予約枠です。");
    }
    this.deleted = true;
  }

}
