CREATE DATABASE IF NOT EXISTS `playground`;

USE `playground`;

CREATE TABLE IF NOT EXISTS `order` (
  `order_id`   CHAR(36)      NOT NULL,
  `symbol`     CHAR(3)       NOT NULL,
  `side`       TINYINT(1)    NOT NULL,
  `quantity`   DECIMAL(36,9) NOT NULL,
  `created_at` DATETIME(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
