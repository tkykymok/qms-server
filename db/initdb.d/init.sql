CREATE DATABASE IF NOT EXISTS qmsdb;
USE qmsdb;

GRANT ALL PRIVILEGES ON qmsdb.* TO 'qms'@'%';
FLUSH PRIVILEGES;

-- 既存のテーブル定義を削除
DROP TABLE IF EXISTS `customers`, `companies`, `stores`, `favorite_stores`, `staffs`, `store_staffs`, `active_staffs`, `reservations`, `reservation_menus`, `sales_histories`, `menus`, `menu_sets`, `menu_set_details`, `notifications`;

SET
    FOREIGN_KEY_CHECKS = 0;


-- customers / 顧客
CREATE TABLE `customers`
(
    `customer_id`     BIGINT PRIMARY KEY COMMENT '顧客ID',
    `cognito_user_id` VARCHAR(255) COMMENT 'CognitoユーザーID',
    `name`            VARCHAR(255) COMMENT '顧客名',
    `email`           VARCHAR(255) COMMENT 'メールアドレス',
    `gender`          INT COMMENT '性別',
    `birthday`        DATE COMMENT '生年月日',
    `created_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      INT                   DEFAULT 0 COMMENT '作成者',
    `created_by_type` VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '作成者タイプ',
    `updated_by`      INT                   DEFAULT 0 COMMENT '更新者',
    `updated_by_type` VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '更新者タイプ'
);

-- companies / 会社
CREATE TABLE `companies`
(
    `company_id`      BIGINT PRIMARY KEY COMMENT '企業ID',
    `company_name`    VARCHAR(255) NOT NULL COMMENT '企業名',
    `created_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      INT                   DEFAULT 0 COMMENT '作成者',
    `created_by_type` VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '作成者タイプ',
    `updated_by`      INT                   DEFAULT 0 COMMENT '更新者',
    `updated_by_type` VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '更新者タイプ'
);

-- stores / 店舗
CREATE TABLE `stores`
(
    `store_id`        BIGINT PRIMARY KEY COMMENT '店舗ID',
    `company_id`      BIGINT COMMENT '企業ID',
    `store_name`      VARCHAR(255) COMMENT '店舗名',
    `address`         VARCHAR(255) COMMENT '住所',
    `location`        GEOMETRY    NOT NULL COMMENT '位置情報',
    `phone_number`    VARCHAR(20) COMMENT '電話番号',
    `business_hours`  VARCHAR(255) COMMENT '営業時間',
    `created_at`      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      INT                  DEFAULT 0 COMMENT '作成者',
    `created_by_type` VARCHAR(45) NOT NULL DEFAULT 'system' COMMENT '作成者タイプ',
    `updated_by`      INT                  DEFAULT 0 COMMENT '更新者',
    `updated_by_type` VARCHAR(45) NOT NULL DEFAULT 'system' COMMENT '更新者タイプ',
    FOREIGN KEY (`company_id`) REFERENCES `companies` (`company_id`)
);

-- favorite_stores / お気に入り店舗
CREATE TABLE `favorite_stores`
(
    `customer_id`     BIGINT COMMENT '顧客ID',
    `store_id`        BIGINT COMMENT '店舗ID',
    `created_at`      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      INT                  DEFAULT 0 COMMENT '作成者',
    `created_by_type` VARCHAR(45) NOT NULL DEFAULT 'system' COMMENT '作成者タイプ',
    `updated_by`      INT                  DEFAULT 0 COMMENT '更新者',
    `updated_by_type` VARCHAR(45) NOT NULL DEFAULT 'system' COMMENT '更新者タイプ',
    PRIMARY KEY (`customer_id`, `store_id`),
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`store_id`)
);

-- staffs / スタッフ
CREATE TABLE `staffs`
(
    `staff_id`        BIGINT PRIMARY KEY COMMENT 'スタッフID',
    `name`            VARCHAR(255) COMMENT 'スタッフ名',
    `cognito_user_id` VARCHAR(255) COMMENT 'CognitoユーザーID',
    `created_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      INT                   DEFAULT 0 COMMENT '作成者',
    `created_by_type` VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '作成者タイプ',
    `updated_by`      INT                   DEFAULT 0 COMMENT '更新者',
    `updated_by_type` VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '更新者タイプ'
);

-- store_staffs / 店舗スタッフ
CREATE TABLE `store_staffs`
(
    `staff_id`        BIGINT COMMENT 'スタッフID',
    `store_id`        BIGINT COMMENT '店舗ID',
    `created_at`      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      INT                  DEFAULT 0 COMMENT '作成者',
    `created_by_type` VARCHAR(45) NOT NULL DEFAULT 'system' COMMENT '作成者タイプ',
    `updated_by`      INT                  DEFAULT 0 COMMENT '更新者',
    `updated_by_type` VARCHAR(45) NOT NULL DEFAULT 'system' COMMENT '更新者タイプ',
    PRIMARY KEY (`staff_id`, `store_id`),
    FOREIGN KEY (`staff_id`) REFERENCES `staffs` (`staff_id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`store_id`)
);

-- active_staffs / アクティブスタッフ
CREATE TABLE `active_staffs`
(
    `store_id`             BIGINT COMMENT '店舗ID',
    `staff_id`             BIGINT       NOT NULL COMMENT 'スタッフID',
    `order`                INT          NOT NULL COMMENT '順番',
    `break_start_datetime` DATETIME COMMENT '休憩開始日時',
    `break_end_datetime`   DATETIME COMMENT '休憩終了日時',
    `created_at`           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`           INT                   DEFAULT 0 COMMENT '作成者',
    `created_by_type`      VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '作成者タイプ',
    `updated_by`           INT                   DEFAULT 0 COMMENT '更新者',
    `updated_by_type`      VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '更新者タイプ',
    PRIMARY KEY (`store_id`, `staff_id`),
    FOREIGN KEY (`staff_id`) REFERENCES `staffs` (`staff_id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`store_id`)
);

-- reservations / 予約
CREATE TABLE `reservations`
(
    `reservation_id`         BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '予約ID',
    `customer_id`            BIGINT       NOT NULL COMMENT '顧客ID',
    `store_id`               BIGINT       NOT NULL COMMENT '店舗ID',
    `staff_id`               BIGINT COMMENT 'スタッフID',
    `reservation_number`     INT          NOT NULL COMMENT '予約番号',
    `reserved_datetime`      DATETIME     NOT NULL COMMENT '予約日時',
    `hold_start_datetime`    DATETIME COMMENT '保留開始日時',
    `service_start_datetime` DATETIME COMMENT 'サービス開始日時',
    `service_end_datetime`   DATETIME COMMENT 'サービス終了日時',
    `status`                 INT          NOT NULL DEFAULT 0 COMMENT 'ステータス',
    `arrival_flag`           BOOLEAN      NOT NULL DEFAULT 0 COMMENT '到着フラグ',
    `cancel_type`            INT COMMENT 'キャンセルタイプ',
    `created_at`             DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`             DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`             INT                   DEFAULT 0 COMMENT '作成者',
    `created_by_type`        VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '作成者タイプ',
    `updated_by`             INT                   DEFAULT 0 COMMENT '更新者',
    `updated_by_type`        VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '更新者タイプ',
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`store_id`),
    FOREIGN KEY (`staff_id`) REFERENCES `staffs` (`staff_id`)
);

-- reservation_menus / 予約メニュー
CREATE TABLE `reservation_menus`
(
    `reservation_id`  BIGINT COMMENT '予約ID',
    `store_id`        BIGINT COMMENT '店舗ID',
    `menu_id`         BIGINT COMMENT 'メニューID',
    `created_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      INT                   DEFAULT 0 COMMENT '作成者',
    `created_by_type` VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '作成者タイプ',
    `updated_by`      INT                   DEFAULT 0 COMMENT '更新者',
    `updated_by_type` VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '更新者タイプ',
    PRIMARY KEY (`reservation_id`, `store_id`, `menu_id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`store_id`),
    FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`reservation_id`)
);

-- sales_histories / 売上履歴
CREATE TABLE `sales_histories`
(
    `sales_history_id` BIGINT PRIMARY KEY COMMENT '売上履歴ID',
    `reservation_id`   BIGINT COMMENT '予約ID',
    `menu_name`        VARCHAR(255) COMMENT 'メニュー名',
    `price`            DECIMAL(10, 2) COMMENT '価格',
    `created_at`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`       INT                   DEFAULT 0 COMMENT '作成者',
    `created_by_type`  VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '作成者タイプ',
    `updated_by`       INT                   DEFAULT 0 COMMENT '更新者',
    `updated_by_type`  VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '更新者タイプ',
    FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`reservation_id`)
);

-- menus / メニュー
CREATE TABLE `menus`
(
    `store_id`        BIGINT COMMENT '店舗ID',
    `menu_id`         BIGINT COMMENT 'メニューID',
    `menu_name`       VARCHAR(255) COMMENT 'メニュー名',
    `price`           DECIMAL(10, 2) COMMENT '価格',
    `time`            INT          NOT NULL DEFAULT 0 COMMENT '所要時間',
    `created_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      INT                   DEFAULT 0 COMMENT '作成者',
    `created_by_type` VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '作成者タイプ',
    `updated_by`      INT                   DEFAULT 0 COMMENT '更新者',
    `updated_by_type` VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '更新者タイプ',
    PRIMARY KEY (`store_id`, `menu_id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`store_id`)
);

-- menu_sets / メニューセット
CREATE TABLE `menu_sets`
(
    `set_id`          BIGINT COMMENT 'セットID',
    `store_id`        BIGINT COMMENT '店舗ID',
    `set_name`        VARCHAR(255) COMMENT 'セット名',
    `set_price`       DECIMAL(10, 2) COMMENT 'セット価格',
    `created_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      INT                   DEFAULT 0 COMMENT '作成者',
    `created_by_type` VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '作成者タイプ',
    `updated_by`      INT                   DEFAULT 0 COMMENT '更新者',
    `updated_by_type` VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '更新者タイプ',
    PRIMARY KEY (`set_id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`store_id`)
);

-- menu_set_details / メニューセット詳細
CREATE TABLE `menu_set_details`
(
    `set_id`          BIGINT COMMENT 'セットID',
    `store_id`        BIGINT COMMENT '店舗ID',
    `menu_id`         BIGINT COMMENT 'メニューID',
    `created_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      INT                   DEFAULT 0 COMMENT '作成者',
    `created_by_type` VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '作成者タイプ',
    `updated_by`      INT                   DEFAULT 0 COMMENT '更新者',
    `updated_by_type` VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '更新者タイプ',
    PRIMARY KEY (`set_id`, `store_id`, `menu_id`),
    FOREIGN KEY (`set_id`) REFERENCES `menu_sets` (`set_id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`store_id`)
);

-- notifications / 通知
CREATE TABLE `notifications`
(
    `notification_id`      BIGINT PRIMARY KEY COMMENT '通知ID',
    `customer_id`          BIGINT COMMENT '顧客ID',
    `reservation_id`       BIGINT COMMENT '予約ID',
    `notification_type`    INT COMMENT '通知タイプ',
    `notification_content` TEXT COMMENT '通知内容',
    `notification_status`  INT COMMENT '通知ステータス',
    `created_at`           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`           INT                   DEFAULT 0 COMMENT '作成者',
    `created_by_type`      VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '作成者タイプ',
    `updated_by`           INT                   DEFAULT 0 COMMENT '更新者',
    `updated_by_type`      VARCHAR(255) NOT NULL DEFAULT 'system' COMMENT '更新者タイプ',
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`),
    FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`reservation_id`)
);

-- companiesテーブルにレコードを2つ挿入
INSERT INTO `companies` (`company_id`, `company_name`)
VALUES (1, 'Company A'),
       (2, 'Company B');

-- storesテーブルにレコードを2つ挿入
INSERT INTO `stores` (`store_id`, `company_id`, `store_name`, `address`, `phone_number`, `business_hours`, `location`)
VALUES (1, 1, 'store_ A1', 'Address A1', '123-456-7890', '9:00-18:00', ST_GeomFromText('POINT(135.0000 35.0000)')),
       (2, 2, 'store_ B1', 'Address B1', '098-765-4321', '10:00-19:00', ST_GeomFromText('POINT(136.0000 36.0000)'));


-- customersテーブルにレコードを5つ挿入
INSERT INTO `customers` (`customer_id`, `cognito_user_id`, `name`, `email`, `gender`, `birthday`)
VALUES (0, null, 'guest', null, null, null),
       (1, 'cognito1', 'Customer A', 'customerA@example.com', 1, '1990-01-01'),
       (2, 'cognito2', 'Customer B', 'customerB@example.com', 2, '1991-02-02'),
       (3, 'cognito3', 'Customer C', 'customerC@example.com', 1, '1992-03-03'),
       (4, 'cognito4', 'Customer D', 'customerD@example.com', 2, '1993-04-04'),
       (5, 'cognito5', 'Customer E', 'customerE@example.com', 1, '1994-05-05'),
       (6, 'cognito6', 'Customer F', 'customerF@example.com', 2, '1995-06-06'),
       (7, 'cognito7', 'Customer G', 'customerG@example.com', 1, '1996-07-07'),
       (8, 'cognito8', 'Customer H', 'customerH@example.com', 2, '1997-08-08'),
       (9, 'cognito9', 'Customer I', 'customerI@example.com', 1, '1998-09-09'),
       (10, 'cognito10', 'Customer J', 'customerJ@example.com', 2, '1999-10-10');


-- reservationsテーブルにレコードを5つ挿入
INSERT INTO `reservations` (`reservation_id`, `customer_id`, `store_id`, `staff_id`, `reservation_number`,
                            `reserved_datetime`, `service_start_datetime`, `service_end_datetime`, `status`,
                            `arrival_flag`, `cancel_type`)
VALUES (1, 1, 1, null, 101, '2023-01-01 10:00:00', '2023-01-01 10:00:00', '2023-01-01 11:00:00', 2, false, null),
       (2, 2, 1, null, 102, '2023-01-02 10:00:00', '2023-01-02 10:00:00', '2023-01-02 11:00:00', 2, false, null),

       (3, 3, 2, null, 101, '2023-01-03 10:00:00', '2023-01-03 10:00:00', '2023-01-03 10:10:00', 2, false, null),
       (4, 4, 2, null, 102, '2023-01-03 10:00:00', '2023-01-03 10:00:00', '2023-01-03 10:11:00', 2, false, null),
       (5, 5, 2, null, 103, '2023-01-03 10:00:00', '2023-01-03 10:00:00', '2023-01-03 10:12:00', 2, false, null),

       (6, 3, 2, null, 104, '2023-02-03 10:00:00', '2023-02-03 10:00:00', '2023-02-03 10:12:00', 2, false, null),
       (7, 4, 2, null, 105, '2023-02-03 10:00:00', '2023-02-03 10:00:00', '2023-02-03 10:13:00', 2, false, null),
       (8, 5, 2, null, 106, '2023-02-03 10:00:00', '2023-02-03 10:00:00', '2023-02-03 10:14:00', 2, false, null),

       (9, 3, 2, null, 107, '2023-03-03 10:00:00', '2023-03-03 10:00:00', '2023-03-03 10:14:00', 2, false, null),
       (10, 4, 2, null, 108, '2023-03-03 10:00:00', '2023-03-03 10:00:00', '2023-03-03 10:15:00', 2, false, null),
       (11, 5, 2, null, 109, '2023-03-03 10:00:00', '2023-03-03 10:00:00', '2023-03-03 10:16:00', 2, false, null),

       (12, 3, 2, 3, 101, now(), now(), null, 1, false, null),
       (13, 4, 2, 4, 102, now(), now(), null, 1, false, null),
       (14, 5, 2, null, 103, now(), null, null, 0, false, null),
       (15, 6, 2, null, 106, now(), null, null, 0, false, null),
       (16, 7, 2, null, 107, now(), null, null, 0, false, null),
       (17, 8, 2, null, 108, now(), null, null, 0, false, null),
       (18, 9, 2, null, 109, now(), null, null, 0, false, null),
       (19, 10, 2, null, 110, now(), null, null, 0, false, null);


INSERT INTO `menus` (`store_id`, `menu_id`, `menu_name`, `price`, `time`)
VALUES (1, 1, 'Menu 1', 1000, 15),
       (2, 1, 'Menu 2', 1500, 15);


INSERT INTO `reservation_menus` (`store_id`, `reservation_id`, `menu_id`)
VALUES (2, 3, 1),
       (2, 4, 1),
       (2, 5, 1),
       (2, 6, 1),
       (2, 7, 1),
       (2, 8, 1),
       (2, 9, 1),
       (2, 10, 1),
       (2, 11, 1),
       (2, 12, 1),
       (2, 13, 1),
       (2, 14, 1),
       (2, 15, 1),
       (2, 16, 1),
       (2, 17, 1),
       (2, 18, 1),
       (2, 19, 1);

-- staffsテーブルへのデータ投入
INSERT INTO `staffs` (`staff_id`, `name`, `cognito_user_id`)
VALUES (1, '山田', 'cognitoA'),
       (2, '鈴木', 'cognitoB'),
       (3, '坂本', 'cognitoC'),
       (4, '田中', 'cognitoD'),
       (5, '小島', 'cognitoE'),
       (6, '後藤', 'cognitoF'),
       (7, 'ダミー1', 'cognitoG'),
       (8, 'ダミー2', 'cognitoH');

-- store_staffテーブルへのデータ投入
INSERT INTO `store_staffs` (`staff_id`, `store_id`)
VALUES (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 2),
       (7, 1),
       (8, 1);


INSERT INTO `active_staffs` (`staff_id`, `store_id`, `order`, `break_start_datetime`, `break_end_datetime`)
VALUES (1, 2, 1, null, null),
       (2, 2, 2, null, null);