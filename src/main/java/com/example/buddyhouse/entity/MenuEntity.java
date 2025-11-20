package com.example.buddyhouse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name ="menus")
public class MenuEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** メニュー名 */
  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false)//日帰りならfalse宿泊ならtrue
  private boolean overnight;   // 日跨ぎするメニューかどうか


  /** メニューの説明、詳細 */
  @Column(columnDefinition = "TEXT")
  private String feature;

  /** 販売状況フラグ  true＝販売中、false=販売停止中*/
  @Column(nullable = false)
  private boolean active = true;

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
