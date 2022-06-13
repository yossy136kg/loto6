
環境
Windows10 (64bit)
Java 11
Spring Boot 2.2.7
thymeleaf 3.0.11
MySQL 5.6




サンプルデータは下記の通り準備します。
URL:https://loto6.thekyo.jp/download/index
データベース: loto
ユーザー： root
テーブル: loto6_data


create database if not exists loto;
create user 'test_user'@'localhost' identified by 'test_user';
grant all on loto.* to 'test_user'@'localhost';
CREATE TABLE `loto6_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lottery_date` date DEFAULT NULL,
  `num1` int(2) NOT NULL,
  `num2` int(2) NOT NULL,
  `num3` int(2) NOT NULL,
  `num4` int(2) NOT NULL,
  `num5` int(2) NOT NULL,
  `num6` int(2) NOT NULL,
  `bonus` int(2) NOT NULL,
  `prise1_cnt` int(11) NOT NULL,
  `prise2_cnt` int(11) NOT NULL,
  `prise3_cnt` int(11) NOT NULL,
  `prise4_cnt` int(11) NOT NULL,
  `prise5_cnt` int(11) NOT NULL,
  `prise1_money` int(11) NOT NULL,
  `prise2_money` int(11) NOT NULL,
  `prise3_money` int(11) NOT NULL,
  `prise4_money` int(11) NOT NULL,
  `prise5_money` int(11) NOT NULL,
  `carryover` int(11) NOT NULL,
  `check_num` char(27) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1499 DEFAULT CHARSET=utf8mb4

update loto6_data set check_num = concat('_', LPAD(`num1`, 2, '0'),'_', LPAD(`num2`, 2, '0'),'_', LPAD(`num3`, 2, '0'),'_', LPAD(`num4`, 2, '0'),'_', LPAD(`num5`, 2, '0'),'_', LPAD(`num6`, 2, '0'),'_', LPAD(`bonus`, 2, '0'));


