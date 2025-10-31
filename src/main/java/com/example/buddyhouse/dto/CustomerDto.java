package com.example.buddyhouse.dto;



import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {
  private Long id;
  private String name;
  private String address;
  private String email;
  private String phone;
  private boolean isDeleted;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
