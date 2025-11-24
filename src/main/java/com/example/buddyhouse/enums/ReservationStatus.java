package com.example.buddyhouse.enums;

public enum ReservationStatus {
  RESERVED,    // 予約確定・来店待ち
  CHECKED_IN,  // 来店して預かり中
  CHECKED_OUT, // 退店済み
  CANCELLED    // キャンセル
}
