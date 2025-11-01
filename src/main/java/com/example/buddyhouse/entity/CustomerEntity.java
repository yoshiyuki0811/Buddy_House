package com.example.buddyhouse.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * 顧客データ
 */

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class CustomerEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; //顧客ID（自動採番）

  @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL,orphanRemoval = true)
  private List<PetsEntity> pets =new ArrayList<>();

  @Column(nullable = false,length = 100)
  private String name; //顧客名

  @Column(nullable = false)
  private String address; //住所

  @Column(nullable = false, unique = true)
  private String email; //メールアドレス

  @Column(nullable = false, length = 20)
  private String phone; //電話番号

  @Column(nullable = false,name ="is_deleted")
  private boolean isDeleted=false;//デフォルトfalse(有効)　削除フラグ

  @CreationTimestamp
  @Column(insertable = false, updatable = false)
  private LocalDateTime createdAt; //登録日時(自動更新)

  @UpdateTimestamp
  @Column(insertable = false)
  private LocalDateTime updatedAt; //更新日時（自動更新）


}
