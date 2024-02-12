DROP
    DATABASE IF EXISTS qms_db;
CREATE
    DATABASE qms_db;
USE qms_db;

GRANT ALL PRIVILEGES ON qms_db.* TO
    'qms'@'%';
FLUSH
    PRIVILEGES;

-- 既存のテーブル定義を削除
DROP TABLE IF EXISTS `customers`, `companies`, `stores`, `store_business_hours`, `favorite_stores`, `staffs`, `store_staffs`, `active_staffs`, `reservations`, `reservation_menus`, `sales`, `menus`, `menu_sets`, `menu_set_details`, `notifications`;

SET
    FOREIGN_KEY_CHECKS = 0;


-- customers / 顧客
CREATE TABLE `customers`
(
    `id`              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    `cognito_user_id` VARCHAR(255) COMMENT 'CognitoユーザーID',
    `last_name`       VARCHAR(255) NOT NULL COMMENT '顧客姓',
    `first_name`      VARCHAR(255) NOT NULL COMMENT '顧客名',
    `email`           VARCHAR(255) COMMENT 'メールアドレス',
    `gender`          INT          NOT NULL COMMENT '性別',
    `birthday`        DATE COMMENT '生年月日',
    `created_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      BIGINT       NOT NULL DEFAULT 0 COMMENT '作成者',
    `created_by_type` INT          NOT NULL DEFAULT 0 COMMENT '作成者タイプ',
    `updated_by`      BIGINT       NOT NULL DEFAULT 0 COMMENT '更新者',
    `updated_by_type` INT          NOT NULL DEFAULT 0 COMMENT '更新者タイプ'
);

-- companies / 会社
CREATE TABLE `companies`
(
    `id`              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    `company_name`    VARCHAR(255) NOT NULL COMMENT '企業名',
    `created_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      BIGINT       NOT NULL DEFAULT 0 COMMENT '作成者',
    `created_by_type` INT          NOT NULL DEFAULT 0 COMMENT '作成者タイプ',
    `updated_by`      BIGINT       NOT NULL DEFAULT 0 COMMENT '更新者',
    `updated_by_type` INT          NOT NULL DEFAULT 0 COMMENT '更新者タイプ'
);

-- stores / 店舗
CREATE TABLE `stores`
(
    `id`              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    `company_id`      BIGINT       NOT NULL COMMENT '企業ID',
    `store_name`      VARCHAR(255) NOT NULL COMMENT '店舗名',
    `postal_code`     VARCHAR(8) COMMENT '郵便番号',
    `address`         VARCHAR(255) COMMENT '住所',
    `latitude`        DECIMAL(10, 7) COMMENT '緯度',
    `longitude`       DECIMAL(10, 7) COMMENT '経度',
    `phone_number`    VARCHAR(20) COMMENT '電話番号',
    `home_page_url`   VARCHAR(255) COMMENT 'ホームページURL',
    `created_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      BIGINT       NOT NULL DEFAULT 0 COMMENT '作成者',
    `created_by_type` INT          NOT NULL DEFAULT 0 COMMENT '作成者タイプ',
    `updated_by`      BIGINT       NOT NULL DEFAULT 0 COMMENT '更新者',
    `updated_by_type` INT          NOT NULL DEFAULT 0 COMMENT '更新者タイプ',
    FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
);

-- store_business_hours / 店舗営業時間
CREATE TABLE `store_business_hours`
(
    `store_id`        BIGINT COMMENT '店舗ID',
    `day_of_week`     INT COMMENT '曜日（0=日曜, 1=月曜, ..., 6=土曜）',
    `open_time`       TIME COMMENT '開店時間',
    `close_time`      TIME COMMENT '閉店時間',
    `closed`          BOOLEAN  NOT NULL DEFAULT FALSE COMMENT '定休日の場合はTRUE',
    `created_at`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      BIGINT   NOT NULL DEFAULT 0 COMMENT '作成者',
    `created_by_type` INT      NOT NULL DEFAULT 0 COMMENT '作成者タイプ',
    `updated_by`      BIGINT   NOT NULL DEFAULT 0 COMMENT '更新者',
    `updated_by_type` INT      NOT NULL DEFAULT 0 COMMENT '更新者タイプ',
    PRIMARY KEY (`store_id`, `day_of_week`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`)
);

-- favorite_stores / お気に入り店舗
CREATE TABLE `favorite_stores`
(
    `customer_id`     BIGINT COMMENT '顧客ID',
    `store_id`        BIGINT COMMENT '店舗ID',
    `created_at`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      BIGINT   NOT NULL DEFAULT 0 COMMENT '作成者',
    `created_by_type` INT      NOT NULL DEFAULT 0 COMMENT '作成者タイプ',
    `updated_by`      BIGINT   NOT NULL DEFAULT 0 COMMENT '更新者',
    `updated_by_type` INT      NOT NULL DEFAULT 0 COMMENT '更新者タイプ',
    PRIMARY KEY (`customer_id`, `store_id`),
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`)
);

-- staffs / スタッフ
CREATE TABLE `staffs`
(
    `id`              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    `company_id`      BIGINT       NOT NULL COMMENT '所属企業ID',
    `last_name`       VARCHAR(255) NOT NULL COMMENT 'スタッフ姓',
    `first_name`      VARCHAR(255) NOT NULL COMMENT 'スタッフ名',
    `image_url`       VARCHAR(255) COMMENT '画像URL',
    `cognito_user_id` VARCHAR(255) COMMENT 'CognitoユーザーID',
    `created_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      BIGINT       NOT NULL DEFAULT 0 COMMENT '作成者',
    `created_by_type` INT          NOT NULL DEFAULT 0 COMMENT '作成者タイプ',
    `updated_by`      BIGINT       NOT NULL DEFAULT 0 COMMENT '更新者',
    `updated_by_type` INT          NOT NULL DEFAULT 0 COMMENT '更新者タイプ',
    FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
);

-- store_staffs / 店舗スタッフ
CREATE TABLE `store_staffs`
(
    `staff_id`        BIGINT COMMENT 'スタッフID',
    `store_id`        BIGINT COMMENT '店舗ID',
    `created_at`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      BIGINT   NOT NULL DEFAULT 0 COMMENT '作成者',
    `created_by_type` INT      NOT NULL DEFAULT 0 COMMENT '作成者タイプ',
    `updated_by`      BIGINT   NOT NULL DEFAULT 0 COMMENT '更新者',
    `updated_by_type` INT      NOT NULL DEFAULT 0 COMMENT '更新者タイプ',
    PRIMARY KEY (`staff_id`, `store_id`),
    FOREIGN KEY (`staff_id`) REFERENCES `staffs` (`id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`)
);

-- active_staffs / 活動スタッフ
CREATE TABLE `active_staffs`
(
    `store_id`         BIGINT COMMENT '店舗ID',
    `staff_id`         BIGINT   NOT NULL COMMENT 'スタッフID',
    `sort_order`       INT      NOT NULL COMMENT '順番',
    `break_start_time` TIME COMMENT '休憩開始日時',
    `break_end_time`   TIME COMMENT '休憩終了日時',
    `created_at`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`       BIGINT   NOT NULL DEFAULT 0 COMMENT '作成者',
    `created_by_type`  INT      NOT NULL DEFAULT 0 COMMENT '作成者タイプ',
    `updated_by`       BIGINT   NOT NULL DEFAULT 0 COMMENT '更新者',
    `updated_by_type`  INT      NOT NULL DEFAULT 0 COMMENT '更新者タイプ',
    PRIMARY KEY (`store_id`, `staff_id`),
    FOREIGN KEY (`staff_id`) REFERENCES `staffs` (`id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`)
);

-- menus / メニュー
CREATE TABLE `menus`
(
    `store_id`        BIGINT COMMENT '店舗ID',
    `store_menu_id`   INT COMMENT '店舗メニューID',
    `menu_name`       VARCHAR(255)   NOT NULL COMMENT 'メニュー名',
    `price`           DECIMAL(10, 0) NOT NULL COMMENT '価格',
    `time`            INT            NOT NULL DEFAULT 0 COMMENT '所要時間',
    `tag_color`       VARCHAR(7)     NOT NULL DEFAULT '#E9E9E9' COMMENT 'タグ色',
    `disabled`        BOOLEAN        NOT NULL DEFAULT 0 COMMENT '無効フラグ',
    `created_at`      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      BIGINT         NOT NULL DEFAULT 0 COMMENT '作成者',
    `created_by_type` INT            NOT NULL DEFAULT 0 COMMENT '作成者タイプ',
    `updated_by`      BIGINT         NOT NULL DEFAULT 0 COMMENT '更新者',
    `updated_by_type` INT            NOT NULL DEFAULT 0 COMMENT '更新者タイプ',
    PRIMARY KEY (`store_id`, `store_menu_id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`)
);

-- reservations / 予約
CREATE TABLE `reservations`
(
    `id`                 BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    `customer_id`        BIGINT   NOT NULL COMMENT '顧客ID',
    `store_id`           BIGINT   NOT NULL COMMENT '店舗ID',
    `reservation_number` INT      NOT NULL COMMENT '予約番号',
    `reserved_date`      DATE     NOT NULL COMMENT '予約日',
    `staff_id`           BIGINT COMMENT 'スタッフID',
    `service_start_time` TIME COMMENT 'サービス開始日時',
    `service_end_time`   TIME COMMENT 'サービス終了日時',
    `hold_start_time`    TIME COMMENT '保留開始日時',
    `status`             INT      NOT NULL DEFAULT 0 COMMENT 'ステータス',
    `notified`           BOOLEAN  NOT NULL DEFAULT 0 COMMENT '順番通知フラグ',
    `arrived`            BOOLEAN  NOT NULL DEFAULT 0 COMMENT '到着フラグ',
    `version`            INT      NOT NULL DEFAULT 0 COMMENT 'バージョン',
    `created_at`         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`         BIGINT   NOT NULL DEFAULT 0 COMMENT '作成者',
    `created_by_type`    INT      NOT NULL DEFAULT 0 COMMENT '作成者タイプ',
    `updated_by`         BIGINT   NOT NULL DEFAULT 0 COMMENT '更新者',
    `updated_by_type`    INT      NOT NULL DEFAULT 0 COMMENT '更新者タイプ',
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`),
    FOREIGN KEY (`staff_id`) REFERENCES `staffs` (`id`)
);

-- reservation_menus / 予約メニュー
CREATE TABLE `reservation_menus`
(
    `reservation_id`  BIGINT COMMENT '予約ID',
    `store_id`        BIGINT COMMENT '店舗ID',
    `store_menu_id`   INT COMMENT '店舗メニューID',
    `created_at`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      BIGINT   NOT NULL DEFAULT 0 COMMENT '作成者',
    `created_by_type` INT      NOT NULL DEFAULT 0 COMMENT '作成者タイプ',
    `updated_by`      BIGINT   NOT NULL DEFAULT 0 COMMENT '更新者',
    `updated_by_type` INT      NOT NULL DEFAULT 0 COMMENT '更新者タイプ',
    PRIMARY KEY (`reservation_id`, `store_id`, `store_menu_id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`),
    FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`)
);

-- sales / 売上
CREATE TABLE `sales`
(
    `id`              BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    `reservation_id`  BIGINT COMMENT '予約ID',
    `menu_name`       VARCHAR(255) COMMENT 'メニュー名',
    `sales_amount`    DECIMAL(10, 0) COMMENT '売上金額',
    `created_at`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      BIGINT   NOT NULL DEFAULT 0 COMMENT '作成者',
    `created_by_type` INT      NOT NULL DEFAULT 0 COMMENT '作成者タイプ',
    `updated_by`      BIGINT   NOT NULL DEFAULT 0 COMMENT '更新者',
    `updated_by_type` INT      NOT NULL DEFAULT 0 COMMENT '更新者タイプ',
    FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`)
);

-- notifications / 通知
CREATE TABLE `notifications`
(
    `id`                BIGINT PRIMARY KEY COMMENT 'ID',
    `store_id`          BIGINT COMMENT '店舗ID',
    `customer_id`       BIGINT COMMENT '顧客ID',
    `notification_type` INT      NOT NULL COMMENT '通知タイプ',
    `reservation_id`    BIGINT COMMENT '予約ID',
    `content`           TEXT COMMENT '通知内容',
    `status`            INT COMMENT '通知ステータス',
    `created_at`        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`        BIGINT   NOT NULL DEFAULT 0 COMMENT '作成者',
    `created_by_type`   INT      NOT NULL DEFAULT 0 COMMENT '作成者タイプ',
    `updated_by`        BIGINT   NOT NULL DEFAULT 0 COMMENT '更新者',
    `updated_by_type`   INT      NOT NULL DEFAULT 0 COMMENT '更新者タイプ',
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`),
    FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`)
);

-- companiesテーブルにレコードを2つ挿入
INSERT INTO `companies` (`id`, `company_name`)
VALUES (1, 'Company A'),
       (2, 'Company B');

-- storesテーブルにレコードを2つ挿入
INSERT INTO `stores` (`id`, `company_id`, `store_name`, `postal_code`, `address`, `phone_number`, `home_page_url`,
                      `latitude`, `longitude`)
VALUES (1, 1, 'store_ A1', '123-4567', '東京都千代田区千代田1-1', '123-456-7890', 'https://www.google.com/', 35.6798787,
        139.7581848),
       (2, 1, 'store_ A2', '123-4567', '東京都千代田区千代田1-2', '123-456-7890', 'https://www.google.com/', 35.6797699,
        139.7574348),
       (3, 2, 'store_ B1', '123-4567', '東京都千代田区千代田2-1', '098-765-4321', 'https://www.google.com/', 36.0000,
        136.0000);

-- store_business_hoursテーブルにレコードを挿入
INSERT INTO `store_business_hours` (`store_id`, `day_of_week`, `open_time`, `close_time`, `closed`)
VALUES (1, 1, '09:00', '18:00', FALSE), -- 日曜日
       (1, 2, '09:00', '18:00', FALSE), -- 月曜日
       (1, 3, '09:00', '18:00', FALSE), -- 火曜日
       (1, 4, '09:00', '18:00', FALSE), -- 水曜日
       (1, 5, '09:00', '18:00', FALSE), -- 木曜日
       (1, 6, '09:00', '20:00', FALSE), -- 金曜日
       (1, 7, NULL, NULL, TRUE),        -- 土曜日（定休日）

       (2, 1, '10:00', '19:00', FALSE), -- 日曜日
       (2, 2, '10:00', '19:00', FALSE), -- 月曜日
       (2, 3, '10:00', '19:00', FALSE), -- 火曜日
       (2, 4, '10:00', '19:00', FALSE), -- 水曜日
       (2, 5, '10:00', '19:00', FALSE), -- 木曜日
       (2, 6, NULL, NULL, TRUE),        -- 金曜日（定休日）
       (2, 7, '10:00', '19:00', FALSE);
-- 土曜日

-- customersテーブルにレコードを5つ挿入
INSERT INTO `customers` (`id`, `cognito_user_id`, `last_name`, `first_name`, `email`, `gender`, `birthday`)
VALUES (1, 'cognito1', 'Customer A', '-', 'customerA@example.com', 1, '1990-01-01'),
       (2, 'cognito2', 'Customer B', '-', 'customerB@example.com', 2, '1991-02-02'),
       (3, 'cognito3', 'Customer C', '-', 'customerC@example.com', 1, '1992-03-03'),
       (4, 'cognito4', 'Customer D', '-', 'customerD@example.com', 2, '1993-04-04'),
       (5, 'cognito5', 'Customer E', '-', 'customerE@example.com', 1, '1994-05-05'),
       (6, 'cognito6', 'Customer F', '-', 'customerF@example.com', 2, '1995-06-06'),
       (7, 'cognito7', 'Customer G', '-', 'customerG@example.com', 1, '1996-07-07'),
       (8, 'cognito8', 'Customer H', '-', 'customerH@example.com', 2, '1997-08-08'),
       (9, 'cognito9', 'Customer I', '-', 'customerI@example.com', 1, '1998-09-09'),
       (10, 'cognito10', 'Customer J', '-', 'customerJ@example.com', 2, '1999-10-10');

-- menuテーブルにレコードを3つ挿入
INSERT INTO `menus` (`store_id`, `store_menu_id`, `menu_name`, `price`, `time`, `created_by`, `updated_by`)
VALUES (1, 101, 'カット', 3000, 15, 1, 1),
       (1, 102, 'カラーリング', 5000, 15, 1, 1),
       (2, 201, 'パーマ', 4500, 15, 2, 2);

-- reservationsテーブルにレコードを5つ挿入
INSERT INTO `reservations` (`customer_id`, `store_id`, `staff_id`, `reservation_number`, `reserved_date`, `status`,
                            `created_by`, `updated_by`)
VALUES (1, 1, null, 1001, CURRENT_DATE(), 0, 1, 1),
       (2, 1, null, 1002, CURRENT_DATE(), 0, 1, 1),
       (3, 2, null, 2001, CURRENT_DATE(), 0, 2, 2),
       (4, 1, null, 1003, CURRENT_DATE(), 0, 1, 1),
       (5, 1, null, 1004, CURRENT_DATE(), 0, 1, 1),
       (6, 1, null, 1005, CURRENT_DATE(), 0, 1, 1),
       (7, 1, null, 1006, CURRENT_DATE(), 0, 1, 1),
       (8, 1, null, 1007, CURRENT_DATE(), 0, 1, 1);

-- reservation_menus / 予約メニュー
INSERT INTO `reservation_menus` (`reservation_id`, `store_id`, `store_menu_id`, `created_by`, `updated_by`)
VALUES (1, 1, 101, 1, 1),
       (1, 1, 102, 1, 1),
       (2, 2, 201, 1, 1),
       (3, 1, 101, 2, 2),
       (4, 1, 101, 1, 1),
       (5, 1, 101, 1, 1),
       (6, 1, 102, 1, 1),
       (7, 1, 101, 1, 1),
       (8, 1, 101, 1, 1);

-- staffsテーブルへのデータ投入
INSERT INTO `staffs` (`id`, `company_id`, `last_name`, `first_name`, `image_url`, `cognito_user_id`)
VALUES (1, 1, '山田', '一郎', null, 'cognitoA'),
       (2, 1, '鈴木', '二郎', null, 'cognitoB'),
       (3, 1, '坂本', '三郎', null, 'cognitoC'),
       (4, 1, '田中', '四郎', null, 'cognitoD'),
       (5, 1, '小島', '五郎', null, 'cognitoE'),
       (6, 1, '後藤', '六郎',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRWTn0y94NNy4YYk6mS36cZB322w1tLr8X9tQ&usqp=CAU',
        'cognitoF'),
       (7, 2, '立花', '七郎',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQBG1nlQyOMKNYug1dHSCAtxYLxRjngUPQu1Q&usqp=CAU',
        'cognitoG'),
       (8, 2, '関口', '八郎',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSSwrE4vldRqY4brqStCQKAwmpxAcRUMqwt-g&usqp=CAU',
        'cognitoH');

-- store_staffテーブルへのデータ投入
INSERT INTO `store_staffs` (`staff_id`, `store_id`)
VALUES (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 1),
       (7, 1),
       (8, 1);

-- active_staffsテーブルへのデータ投入
INSERT INTO `active_staffs` (`staff_id`, `store_id`, `sort_order`, `break_start_time`, `break_end_time`)
VALUES (6, 1, 1, null, null),
       (7, 1, 2, null, null),
       (8, 1, 3, null, null),
       (1, 2, 2, null, null);


-- 以下、一旦未使用 複数メニューを選択した場合にセット価格になる場合があったら使用する
/**
-- menu_sets / メニューセット
CREATE TABLE `menu_sets`
(
    `id`              BIGINT PRIMARY KEY COMMENT 'ID',
    `store_id`        BIGINT COMMENT '店舗ID',
    `set_name`        VARCHAR(255) COMMENT 'セット名',
    `set_price`       DECIMAL(10, 2) COMMENT 'セット価格',
    `created_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新日時',
    `created_by`      INT                   DEFAULT 0 COMMENT '作成者',
    `created_by_type` INT NOT NULL DEFAULT 0 COMMENT '作成者タイプ',
    `updated_by`      INT                   DEFAULT 0 COMMENT '更新者',
    `updated_by_type` INT NOT NULL DEFAULT 0 COMMENT '更新者タイプ',
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`)
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
    `created_by_type` INT NOT NULL DEFAULT 0 COMMENT '作成者タイプ',
    `updated_by`      INT                   DEFAULT 0 COMMENT '更新者',
    `updated_by_type` INT NOT NULL DEFAULT 0 COMMENT '更新者タイプ',
    PRIMARY KEY (`set_id`, `store_id`, `menu_id`),
    FOREIGN KEY (`set_id`) REFERENCES `menu_sets` (`id`),
    FOREIGN KEY (`store_id`) REFERENCES `stores` (`id`)
);
*/