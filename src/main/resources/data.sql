CREATE TABLE IF NOT EXISTS `tb_application_parameters` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `key` VARCHAR(255) NOT NULL,
  `value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE IF NOT EXISTS `tb_swift_heart_beat` (
  `correlation_id` VARCHAR(255) NOT NULL,
  `req_time_stamp` DATETIME NULL,
  `rep_time_stamp` DATETIME NULL,
  `elapsed_time_in_min` INT NULL,
  `alarm_active` VARCHAR(20) NULL,
  `alarmist_check` VARCHAR(45) NULL,
  PRIMARY KEY (`correlation_id`));
  
CREATE TABLE IF NOT EXISTS `shedlock` (
  `name` VARCHAR(64),
  `lock_until` TIMESTAMP(3) NULL,
  `locked_at` TIMESTAMP(3) NULL,
  `locked_by` VARCHAR(255),
  PRIMARY KEY (`name`)
);

INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(1,'SENDER_BIC', 'SENDER_BIC');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(2,'RECEIVER_BIC', 'RECEIVER_BIC');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(3,'ELAPSED_TIME', '8');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(4,'SENDER_MON_START_TIME', '10:00');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(5,'SENDER_MON_END_TIME', '23:59');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(6,'SENDER_TUES_START_TIME', '10:00');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(7,'SENDER_TUES_END_TIME', '23:59');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(8,'SENDER_WED_START_TIME', '10:00');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(9,'SENDER_WED_END_TIME', '23:59');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(10,'SENDER_THUR_START_TIME', '10:00');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(11,'SENDER_THUR_END_TIME', '23:59');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(12,'SENDER_FRI_START_TIME', '10:00');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(13,'SENDER_FRI_END_TIME', '23:59');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(14,'SENDER_SAT_START_TIME', '10:00');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(15,'SENDER_SAT_END_TIME', '23:59');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(16,'SENDER_SUN_START_TIME', '10:00');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(17,'SENDER_SUN_END_TIME', '23:59');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(18,'ALARMIST_MON_START_TIME', '10:00');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(19,'ALARMIST_MON_END_TIME', '23:59');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(20,'ALARMIST_TUES_START_TIME', '10:00');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(21,'ALARMIST_TUES_END_TIME', '23:59');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(22,'ALARMIST_WED_START_TIME', '10:00');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(23,'ALARMIST_WED_END_TIME', '23:59');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(24,'ALARMIST_THUR_START_TIME', '10:00');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(25,'ALARMIST_THUR_END_TIME', '23:59');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(26,'ALARMIST_FRI_START_TIME', '10:00');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(27,'ALARMIST_FRI_END_TIME', '23:59');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(28,'ALARMIST_SAT_START_TIME', '10:00');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(29,'ALARMIST_SAT_END_TIME', '23:59');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(30,'ALARMIST_SUN_START_TIME', '10:00');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(31,'ALARMIST_SUN_END_TIME', '23:59');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(32,'SMTP_HOST', 'smtp.gmail.com');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(33,'SMTP_PORT', '587');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(34,'SMTP_AUTH', 'true');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(35,'SMTP_STARTTLS_ENABLE', 'true');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(36,'SMTP_SENDER', 'xyzsender@gmail.com');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(37,'SMTP_SENDER_PASSWORD', 'password');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(38,'RECIPIENT', 'recepient@gmail.com');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(39,'EMAIL_SUBJECT', 'Swift Heart-Beat Check');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(40,'EMAIL_BODY', 'Hi,' || CHAR(10) || CHAR(10) || 'Received request to check swift heart-beat.' || CHAR(10) || CHAR(10) || 'Thanks & Regards,' || CHAR(10) || 'Team');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(41,'HOUSEKEEPING_TERM_IN_DAYS', '60');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(42,'SENDER_CRON_SCHEDULER', '0 */10 * * * *');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(43,'ALARMIST_CRON_SCHEDULER', '0 */10 * * * 1-5');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(44,'REQ_QUEUE_NAME', 'req_swift_heart_beat_queue');
INSERT INTO tb_application_parameters(`id`,`key`,`value`) VALUES(45,'REP_QUEUE_NAME', 'rep_swift_heart_beat_queue');
