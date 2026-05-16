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
@Entity
@AllArgsConstructor
@Builder
@Table(name = "menus")
public class MenuEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** メニュー名 */
  @Column(nullable = false, length = 50)
  private String name;

  @Enumerated(EnumType.STRING)//(OVERNIGHT,DAYCARE)
  @Column(name = "reservation_type", nullable = false, length = 20)
  private ReservationType reservationType;


  /** メニューの説明、詳細 */
  @Column(columnDefinition = "TEXT")
  private String feature;

  /** 販売状況フラグ  true＝販売中、false=販売停止中*/
  @Builder.Default
  @Column(nullable = false)
  private boolean active = true;

  /** 削除フラグ */
  @Builder.Default
  @Column(nullable = false)
  private boolean deleted = false;

  /** 登録日時 */
  @CreationTimestamp
  @Builder.Default
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  /** 更新日時 */
  @Builder.Default
  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt = LocalDateTime.now();

  public void delete(){
    if (deleted){
      throw new RuntimeException("このメニューはすでに削除済みです");
    }
    this.deleted=true;
  }
}

