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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "pets")
public class PetsEntity {
  /** ペットを瞬時に判別するID（自動採番） */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pets_id;

  /** 飼い主ID（外部キー） */
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "customer_id", nullable = false)
  private CustomerEntity customer;

  /** ペット名 */
  @Column(nullable = false, length = 50)
  private String name;

  /** 犬種 */
  @Column(nullable = false, length = 50)
  private String breed;

  /** 体重区分（Toy, Small, Medium, Large, Giant）←一旦フロントで制御する予定 */
  @Column(nullable = false, length = 10)
  private String weight;

  /** 年齢 */
  @Column
  private Integer age;

  /** 特徴・性格メモ（ホテル側UIに表示） */
  @Column(columnDefinition = "TEXT")
  private String feature;

  /** 削除フラグ */
  @Column(nullable = false)
  private boolean isDeleted = false;

  /** 登録日時 */
  @CreationTimestamp
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  /** 更新日時 */
  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt = LocalDateTime.now();









}
