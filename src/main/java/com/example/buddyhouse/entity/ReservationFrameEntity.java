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
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="reservation_frame")
public class ReservationFrameEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Enumerated(EnumType.STRING)//(OVERNIGHT,DAYCARE)
  @Column(name = "frame_type", nullable = false, length = 20)
  private ReservationType reservationType;

  @Column(name = "start_at", nullable = false)
  private LocalDateTime startAt;

  @Column(name = "end_at", nullable = false)
  private LocalDateTime endAt;

  @Column(name = "max_dogs", nullable = false)
  private Integer maxDogs;

  @Column(name = "used_dogs", nullable = false)
  private Integer usedDogs;

  @Column(name = "is_open", nullable = false)
  private boolean open=true;//trueが販売中falseが満室

  /** 削除フラグ */
  @Column(nullable = false)
  private boolean deleted = false;

  /** 登録日時 */
  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  /** 更新日時 */
  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt = LocalDateTime.now();


}
