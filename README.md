# Date Arithmetic -日付加減算- の使い方

## 実行環境

- Windows10
- SQL 5.7.33
- Eclipse


## データベースの設定を行う

- `application.properties`の`spring.datasource.password`を変更してください。

## データベースの作成

- date_addition_subtractionデータベースの作成

```SQL
CREATE DATABASE date_addition_subtraction;
```

- addition_subtractionテーブルの作成

```SQL
CREATE TABLE addition_subtraction (
  addition_subtraction_id INT PRIMARY KEY AUTO_INCREMENT,
  addition_subtraction_code VARCHAR(30) NOT NULL,
  year INT NOT NULL,
  month INT NOT NULL,
  day INT NOT NULL,
  explanation VARCHAR(50),
  user_id INT NOT NULL,
  FOREIGN KEY fk_user_id(user_id) REFERENCES users(user_id)
) DEFAULT CHARSET=utf8;
```
※macの場合は、`DEFAULT CHARSET=utf8`は外してください

- usersテーブルの作成

```SQL
CREATE TABLE addition_subtraction (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  mail_address VARCHAR(50) NOT NULL,
  nickname VARCHAR(40) NOT NULL,
  password VARCHAR(200) NOT NULL,
  role VARCHAR(20) NOT NULL
) DEFAULT CHARSET=utf8;
```
※macの場合は、`DEFAULT CHARSET=utf8`は外してください


## インメモリ認証を有効にする

- ログインできなかった場合、一度インメモリ認証を有効にしてログインできるか確認してください。
- SecurityConfig.javaのインメモリ認証のコードがコメントアウトしてあるので、外してください。

