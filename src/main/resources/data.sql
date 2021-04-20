CREATE TABLE IF NOT EXISTS tb_application_parameters (
  `id` INT NOT NULL AUTO_INCREMENT,
  `key` VARCHAR(255) NOT NULL,
  `value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`));
  
  CREATE TABLE IF NOT EXISTS tb_swift_heart_beat (
  `correlation_id` VARCHAR(255) NOT NULL,
  `req_time_stamp` DATETIME NULL,
  `rep_time_stamp` DATETIME NULL,
  `elapsed_time_in_min` INT NULL,
  `alarm_active` BOOLEAN NULL,
  `alarmsit_check` VARCHAR(45) NULL,
  PRIMARY KEY (`correlation_id`));
  
CREATE TABLE shedlock (
  name VARCHAR(64),
  lock_until TIMESTAMP(3) NULL,
  locked_at TIMESTAMP(3) NULL,
  locked_by VARCHAR(255),
  PRIMARY KEY (name)
);

INSERT INTO tb_application_parameters(id,key,value) VALUES(1,'SENDER_BIC', 'SENDER_BIC');
INSERT INTO tb_application_parameters(id,key,value) VALUES(2,'RECEIVER_BIC', 'RECEIVER_BIC');
INSERT INTO tb_application_parameters(id,key,value) VALUES(3,'ELAPSED_TIME', '8');
INSERT INTO tb_application_parameters(id,key,value) VALUES(4,'MON_START_TIME', '10:00');
INSERT INTO tb_application_parameters(id,key,value) VALUES(5,'MON_END_TIME', '23:59');
INSERT INTO tb_application_parameters(id,key,value) VALUES(6,'TUES_START_TIME', '10:00');
INSERT INTO tb_application_parameters(id,key,value) VALUES(7,'TUES_END_TIME', '23:59');
INSERT INTO tb_application_parameters(id,key,value) VALUES(8,'WED_START_TIME', '10:00');
INSERT INTO tb_application_parameters(id,key,value) VALUES(9,'WED_END_TIME', '23:59');
INSERT INTO tb_application_parameters(id,key,value) VALUES(10,'THUR_START_TIME', '10:00');
INSERT INTO tb_application_parameters(id,key,value) VALUES(11,'THUR_END_TIME', '23:59');
INSERT INTO tb_application_parameters(id,key,value) VALUES(12,'FRI_START_TIME', '10:00');
INSERT INTO tb_application_parameters(id,key,value) VALUES(13,'FRI_END_TIME', '23:59');
INSERT INTO tb_application_parameters(id,key,value) VALUES(14,'SAT_START_TIME', '10:00');
INSERT INTO tb_application_parameters(id,key,value) VALUES(15,'SAT_END_TIME', '23:59');
INSERT INTO tb_application_parameters(id,key,value) VALUES(16,'SUN_START_TIME', '10:00');
INSERT INTO tb_application_parameters(id,key,value) VALUES(17,'SUN_END_TIME', '23:59');
INSERT INTO tb_application_parameters(id,key,value) VALUES(18,'SMTP_HOST', 'smtp.gmail.com');
INSERT INTO tb_application_parameters(id,key,value) VALUES(19,'SMTP_PORT', '587');
INSERT INTO tb_application_parameters(id,key,value) VALUES(20,'SMTP_AUTH', 'true');
INSERT INTO tb_application_parameters(id,key,value) VALUES(21,'SMTP_STARTTLS_ENABLE', 'true');
INSERT INTO tb_application_parameters(id,key,value) VALUES(22,'SMTP_SENDER', 'xyzsender@gmail.com');
INSERT INTO tb_application_parameters(id,key,value) VALUES(23,'SMTP_SENDER_PASSWORD', 'password');
INSERT INTO tb_application_parameters(id,key,value) VALUES(24,'RECIPIENT', 'recepient@gmail.com');
INSERT INTO tb_application_parameters(id,key,value) VALUES(25,'EMAIL_SUBJECT', 'Swift Heart-Beat Check');
INSERT INTO tb_application_parameters(id,key,value) VALUES(26,'EMAIL_BODY', 'Hi,' || CHAR(10) || CHAR(10) || 'Received request to check swift heart-beat.' || CHAR(10) || CHAR(10) || 'Thanks & Regards,' || CHAR(10) || 'Team');
INSERT INTO tb_application_parameters(id,key,value) VALUES(27,'HOUSEKEEPING_TERM_IN_DAYS', '60');
