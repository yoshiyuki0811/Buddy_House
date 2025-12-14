package com.example.buddyhouse.entity;

import com.example.buddyhouse.enums.ReservationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "reservation_frame")
public class ReservationFrameEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Enumerated(EnumType.STRING)//(OVERNIGHT,DAYCARE)
  @Column(name = "reservation_type", nullable = false, length = 20)
  private ReservationType reservationType;

  @Column(name = "start_at", nullable = false)
  private LocalDateTime startAt;

  @Column(name = "end_at", nullable = false)
  private LocalDateTime endAt;

  @Column(name = "max_dogs", nullable = false)
  private Integer maxDogs;

  @Builder.Default
  @Column(name = "used_dogs", nullable = false)
  private Integer usedDogs=0;

  @Column(name = "is_open", nullable = false)
  @Builder.Default
  private boolean open =true;//trueが販売中falseが満室

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

  public void delete() {
    if (this.deleted) {
      throw new IllegalStateException("すでに削除済みの予約枠です。");
    }
    if (this.usedDogs > 0) {
      throw new IllegalStateException("すでに予約があるため削除できません。");
    }
    this.deleted = true;
  }

  public void frameClose() {
    if (open) {
      throw new IllegalStateException("すでに満室です");
    }
    this.open = true;
  }

  //予約枠の空きチェックと予約のカウント
  public void addUsedDogs(int count) {
    if (this.usedDogs + count > this.maxDogs) {
      throw new IllegalStateException("枠を超えています");
    }
    this.usedDogs += count;
  }
public void removeDogs(int count){
    if (this.usedDogs-count<0) {
      throw new IllegalStateException("枠を戻す数が多すぎます");
    }
    this.usedDogs-=count;
}
}
