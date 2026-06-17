# Buddy House フロントエンド

ペットホテル向けWeb予約管理アプリケーションのReactフロントエンドです。

## 技術スタック

- **React 18** - UIライブラリ
- **TypeScript** - 型安全性
- **Vite** - ビルドツール
- **React Router** - ルーティング
- **TanStack Query** - データ取得・キャッシング
- **Zustand** - 状態管理
- **Axios** - HTTP クライアント
- **Tailwind CSS** - スタイリング

## セットアップ

### インストール

```bash
cd frontend
npm install
```

### 環境変数の設定

```bash
cp .env.example .env.local
```

`.env.local` を編集してバックエンドAPIのURLを設定します：

```
VITE_API_BASE_URL=https://buddy-house-app.com
```

### 開発サーバー起動

```bash
npm run dev
```

http://localhost:3000 でアプリケーションが起動します。

## ビルド

```bash
npm run build
```

## プロジェクト構成

```
frontend/
├── src/
│   ├── pages/              # ページコンポーネント
│   │   ├── customer/       # 会員機能
│   │   └── admin/          # 管理者機能
│   ├── components/         # 再利用可能なコンポーネント
│   ├── layouts/            # レイアウトコンポーネント
│   ├── services/           # API通信
│   ├── store/              # 状態管理
│   ├── types/              # TypeScript型定義
│   ├── App.tsx             # ルートコンポーネント
│   └── main.tsx            # エントリーポイント
├── public/                 # 静的ファイル
├── index.html              # HTMLテンプレート
├── vite.config.ts          # Vite設定
├── tailwind.config.js      # Tailwind CSS設定
└── package.json            # 依存関係
```

## 主な機能

### 会員（CUSTOMER）機能

- **ログイン・登録**: JWT認証
- **ペット管理**: ペット情報の登録・表示
- **予約作成**: 予約枠・メニュー・ペットを選択して予約
- **予約履歴**: 自分の予約一覧を確認
- **プロフィール**: 顧客情報の表示

### 管理者（ADMIN）機能

- **ダッシュボード**: 統計情報の表示
- **顧客管理**: 顧客一覧・詳細表示
- **ペット管理**: 全ペット一覧の表示
- **メニュー管理**: メニュー情報の管理
- **予約枠管理**: 予約枠の作成・削除・関閉
- **予約管理**: 予約一覧・日付検索・詳細表示

## テストアカウント

```
管理者(ADMIN):
  メール: admin
  パスワード: password123

会員(CUSTOMER):
  メール: test@example.com
  パスワード: password123
```

## APIエンドポイント

バックエンドAPIの詳細は以下を参照：

https://buddy-house-app.com/swagger-ui/index.html#/

## トラブルシューティング

### CORS エラーが発生する場合

バックエンドのCORSConfig設定を確認してください。

### ログイン後もページが見つからない場合

- ロール（ADMIN/CUSTOMER）の確認
- localStorage の accessToken を確認
- ブラウザの開発者ツールでコンソールエラーを確認

## 今後の拡張予定

- [ ] ダークモード対応
- [ ] 多言語対応（日本語・英語）
- [ ] 予約キャンセル機能の実装
- [ ] 予約詳細ページの充実
- [ ] エラーハンドリングの改善
- [ ] フォーム検証の追加
- [ ] ローディング状態の改善
- [ ] ユニットテストの追加
- [ ] E2Eテストの追加

## ライセンス

MIT
