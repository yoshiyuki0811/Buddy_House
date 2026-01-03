# Buddy House 

## 制作背景
サービスの概要は、実家で営むペットホテルの業務を効率化するために開発した
ペットホテル向けWeb予約管理アプリケーションです。


顧客・ペット・予約・予約枠・メニューを一元管理し、  
管理者と会員（顧客）それぞれの立場で操作できる構成になっています。

---

## アプリURL
https://buddy-house-app.com/swagger-ui/index.html#/

---

## ER図
<img src="docs/images/er-diagram.png" width="900">

---

## 使用技術

### バックエンド
- Java 21
- Spring Boot
- Spring Security（JWT認証）
- JPA / Hibernate
- MySQL
- Gradle

### インフラ・その他
- AWS（EC2 / RDS）
- Swagger（APIドキュメント）
- GitHub（バージョン管理）

---

## 機能一覧

### 認証・認可
- 会員登録
- ログイン（JWT認証）
- ロール管理（ADMIN / CUSTOMER）

### 顧客（会員）機能
- 自分の顧客情報取得（/me）
- ペット情報の登録・削除
- 自分の予約一覧取得（/reservation/me）
- 予約の新規作成

### 管理者機能
- メニュー管理（作成・販売停止・論理削除）
- 予約枠管理（作成・満室制御・削除）
- 顧客情報の検索・閲覧
- 顧客管理（登録・編集・検索・閲覧・論理削除）

---

## 画面・API仕様

- Swagger UI  
