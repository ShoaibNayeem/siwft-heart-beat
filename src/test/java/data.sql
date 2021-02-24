CREATE TABLE IF NOT EXISTS tb_application_parameters (
id INT AUTO_INCREMENT PRIMARY KEY,
key VARCHAR NOT NULL,
value VARHAR NOT NULL
);
INSERT INTO tb_application_parameters(id,key,value) VALUES(1,'sender_bic', 'SENDER_BIC');
INSERT INTO tb_application_parameters(id,key,value) VALUES(2,'receiver_bic', 'RECEIVER_BIC');
INSERT INTO tb_application_parameters(id,key,value) VALUES(3,'elapsed_time', '8');
INSERT INTO tb_application_parameters(id,key,value) VALUES(4,'sender_bic', '10:00');
INSERT INTO tb_application_parameters(id,key,value) VALUES(5,'sender_bic', '01:00');

