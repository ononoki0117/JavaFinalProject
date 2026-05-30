# PawMart

Spring Boot で構築した、ペット用品向けの EC Web アプリケーションです。  
商品一覧の閲覧、カート追加、決済フロー、ログイン/会員登録、ペットプロフィール表示、レビュー確認機能を備えています。

## 概要

このプロジェクトは、ペット関連商品を閲覧し、購入までの流れを体験できる Web サービスです。  
Thymeleaf を用いたサーバーサイドレンダリング構成で実装しており、JWT 認証とセッション管理を組み合わせています。

## 主な機能

- 商品一覧の表示
- 商品詳細ページ
- カート追加と合計金額の確認
- 決済ページへの遷移と決済完了処理
- 会員登録 / ログイン
- ペットプロフィール情報の表示
- 商品レビューの表示
- Spring Security と JWT による認証処理

## 技術スタック

- Java 17
- Spring Boot 3.3.x
- Spring Web
- Spring Security
- Spring Data MongoDB
- Spring Data JPA
- Thymeleaf
- JWT
- Gradle

## ディレクトリ構成

- `src/main/java`: アプリケーションの主要ロジック
- `src/main/resources/templates`: Thymeleaf テンプレート
- `src/main/resources/static`: 静的アセット
- `src/main/resources/application.yml`: サーバー設定と MongoDB 設定

## 起動方法

### 1. 必要環境

- JDK 17 以上
- MongoDB に接続できる環境
- Gradle 実行環境

### 2. 設定

`src/main/resources/application.yml` で MongoDB 接続情報とサーバーポートを確認、または環境に合わせて修正してください。

現在のデフォルト設定は以下です。

- サーバーポート: `10700`
- MongoDB URI: `spring.data.mongodb.uri`

### 3. 起動

```bash
./gradlew bootRun
```

Windows の場合は次のコマンドを使用できます。

```bash
gradlew.bat bootRun
```

ブラウザから次の URL にアクセスします。

```text
http://localhost:10700
```

## 補足

- 現在の設定では MongoDB の接続情報が `application.yml` に含まれています。公開リポジトリとして運用する場合は、機密情報を環境変数へ分離することを推奨します。
- 決済機能は、実際の PG 連携というより学習・ポートフォリオ用途に近い実装です。

## ライセンス

MIT License を採用しています。詳細は [LICENSE](./LICENSE) を参照してください。
