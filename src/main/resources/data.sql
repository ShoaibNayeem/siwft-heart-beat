
INSERT INTO tb_application_parameters(id,key,value) VALUES(1,'SENDER_BIC', 'SENDER_BIC');
INSERT INTO tb_application_parameters(id,key,value) VALUES(2,'RECEIVER_BIC', 'RECEIVER_BIC');
INSERT INTO tb_application_parameters(id,key,value) VALUES(3,'ELAPSED_TIME', '8');
INSERT INTO tb_application_parameters(id,key,value) VALUES(4,'START_TIME', '10:00');
INSERT INTO tb_application_parameters(id,key,value) VALUES(5,'END_TIME', '23:59');
INSERT INTO tb_application_parameters(id,key,value) VALUES(6,'SMTP_HOST', 'smtp.gmail.com');
INSERT INTO tb_application_parameters(id,key,value) VALUES(7,'SMTP_PORT', '587');
INSERT INTO tb_application_parameters(id,key,value) VALUES(8,'SMTP_AUTH', 'true');
INSERT INTO tb_application_parameters(id,key,value) VALUES(9,'SMTP_STARTTLS_ENABLE', 'true');
INSERT INTO tb_application_parameters(id,key,value) VALUES(10,'SMTP_SENDER', 'xyzsender@gmail.com');
INSERT INTO tb_application_parameters(id,key,value) VALUES(11,'SMTP_SENDER_PASSWORD', 'password');
INSERT INTO tb_application_parameters(id,key,value) VALUES(12,'RECIPIENT', 'recepient@gmail.com');
INSERT INTO tb_application_parameters(id,key,value) VALUES(13,'EMAIL_SUBJECT', 'Swift Heart-Beat Check');
INSERT INTO tb_application_parameters(id,key,value) VALUES(14,'EMAIL_BODY', 'Hi,' || CHAR(10) || CHAR(10) || 'Received request to check swift heart-beat.' || CHAR(10) || CHAR(10) || 'Thanks & Regards,' || CHAR(10) || 'Team');
INSERT INTO tb_application_parameters(id,key,value) VALUES(15,'HOUSEKEEPING_TERM', '0');
