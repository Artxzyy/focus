CREATE TABLE school (
id INT PRIMARY KEY,
name VARCHAR(64) NOT NULL UNIQUE,
max_students INT NOT NULL DEFAULT 5,
address VARCHAR(32) NOT NULL
);
CREATE TABLE person (
school_id INT NOT NULL,
id INT NOT NULL,
first_name VARCHAR(32) NOT NULL,
surname VARCHAR(32),
login VARCHAR(64) NOT NULL UNIQUE,
email VARCHAR(128) NOT NULL UNIQUE,
password VARCHAR(64) NOT NULL,
qtt_answers INT DEFAULT 0,
qtt_wrong_answers INT DEFAULT 0,
qtt_sum_answers INT DEFAULT 0,
qtt_correct_sum_answers INT DEFAULT 0,
qtt_sub_answers INT DEFAULT 0,
qtt_correct_sub_answers INT DEFAULT 0,
qtt_mul_answers INT DEFAULT 0,
qtt_correct_mul_answers INT DEFAULT 0,
qtt_div_answers INT DEFAULT 0,
qtt_correct_div_answers INT DEFAULT 0,
PRIMARY KEY (id),
FOREIGN KEY (school_id) REFERENCES school(id)
);
CREATE TABLE professor (
person_id INT NOT NULL,
FOREIGN KEY (person_id) REFERENCES person(id)
);
CREATE TABLE student (
person_id INT NOT NULL,
professor_id INT NOT NULL,
FOREIGN KEY (person_id) REFERENCES person(id),
FOREIGN KEY (professor_id) REFERENCES person(id)
);
CREATE TABLE content (
professor_id INT NOT NULL,
id INT NOT NULL,
title VARCHAR(32) NOT NULL,
subject VARCHAR(32) NOT NULL,
theme VARCHAR(32),
text TEXT NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (professor_id) REFERENCES person(id)
);
CREATE TABLE activity (
professor_id INT NOT NULL,
id INT NOT NULL,
title VARCHAR(32) NOT NULL,
subject VARCHAR(32) NOT NULL,
theme VARCHAR(32),
statement TEXT NOT NULL,
difficulty DECIMAL DEFAULT 2.5,
qtt_answers INT DEFAULT 0,
qtt_wrong_answers INT DEFAULT 0,
PRIMARY KEY (id),
FOREIGN KEY (professor_id) REFERENCES person(id)
);
CREATE TABLE option (
activity_id INT NOT NULL,
id INT NOT NULL,
text TEXT NOT NULL,
is_correct BOOLEAN NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (activity_id) REFERENCES activity(id)
);
CREATE TABLE message (
sender_id INT NOT NULL,
addresee_id INT NOT NULL,
id INT NOT NULL,
subject VARCHAR(64) DEFAULT 'No subject was writen.',
body TEXT NOT NULL,
date DATE NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (sender_id) REFERENCES person(id),
FOREIGN KEY (addresee_id) REFERENCES person(id)
);
INSERT INTO school VALUES (1, 'Escola de Id 1', 15, '00000-000, 100');
INSERT INTO school VALUES (2, 'Escola de Id 2', 25, '00000-001, 101');
INSERT INTO school VALUES (3, 'Escola de Id 3', 35, '00000-002, 102');
INSERT INTO person VALUES (1, 1, 'Nome 1', 'Sobrenome 1', 'login_prof1', 'person1@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO person VALUES (1, 2, 'Nome 2', 'Sobrenome 2', 'login_prof2', 'person2@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO person VALUES (2, 3, 'Nome 3', 'Sobrenome 3', 'login_prof3', 'person3@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO person VALUES (2, 4, 'Nome 4', 'Sobrenome 4', 'login_prof4', 'person4@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO person VALUES (3, 5, 'Nome 5', 'Sobrenome 5', 'login_prof5', 'person5@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO person VALUES (3, 6, 'Nome 6', 'Sobrenome 6', 'login_prof6', 'person6@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO person VALUES (1, 7, 'Nome 7', 'Sobrenome 7', 'login_aluno1', 'person7@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO person VALUES (1, 8, 'Nome 8', 'Sobrenome 8', 'login_aluno2', 'person8@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO person VALUES (2, 9, 'Nome 9', 'Sobrenome 9', 'login_aluno3', 'person9@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO person VALUES (2, 10, 'Nome 10', 'Sobrenome 10', 'login_aluno4', 'person10@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO person VALUES (3, 11, 'Nome 11', 'Sobrenome 11', 'login_aluno5', 'person11@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO person VALUES (3, 12, 'Nome 12', 'Sobrenome 12', 'login_aluno6', 'person12@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO person VALUES (3, 13, 'Nome 13', 'Sobrenome 12', 'login_aluno7', 'person13@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO person VALUES (3, 14, 'Nome 14', 'Sobrenome 12', 'login_aluno8', 'person14@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO professor VALUES (1);
INSERT INTO professor VALUES (2);
INSERT INTO professor VALUES (3);
INSERT INTO professor VALUES (4);
INSERT INTO professor VALUES (5);
INSERT INTO professor VALUES (6);
INSERT INTO student VALUES (7, 1);
INSERT INTO student VALUES (8, 2);
INSERT INTO student VALUES (9, 3);
INSERT INTO student VALUES (10, 4);
INSERT INTO student VALUES (11, 5);
INSERT INTO student VALUES (12, 6);
INSERT INTO student VALUES (13, 6);
INSERT INTO student VALUES (14, 6);
INSERT INTO content VALUES (1, 1, 'T??tulo de conte??do 1', 'Disciplina de conte??do 1', 'Mat??ria de conte??do 1', 'Texto explicando a mat??ria do conte??do 1');
INSERT INTO content VALUES (2, 2, 'T??tulo de conte??do 2', 'Disciplina de conte??do 2', 'Mat??ria de conte??do 2', 'Texto explicando a mat??ria do conte??do 2');
INSERT INTO content VALUES (3, 3, 'T??tulo de conte??do 3', 'Disciplina de conte??do 3', 'Mat??ria de conte??do 3', 'Texto explicando a mat??ria do conte??do 3');
INSERT INTO content VALUES (4, 4, 'T??tulo de conte??do 4', 'Disciplina de conte??do 4', 'Mat??ria de conte??do 4', 'Texto explicando a mat??ria do conte??do 4');
INSERT INTO content VALUES (5, 5, 'T??tulo de conte??do 5', 'Disciplina de conte??do 5', 'Mat??ria de conte??do 5', 'Texto explicando a mat??ria do conte??do 5');
INSERT INTO content VALUES (6, 6, 'T??tulo de conte??do 6', 'Disciplina de conte??do 6', 'Mat??ria de conte??do 6', 'Texto explicando a mat??ria do conte??do 6');
INSERT INTO activity VALUES (1, 1, 'T??tulo de atividade 1', 'Disciplina de atividade 1', 'Mat??ria de atividade 1', 'Enunciado da atividade 1');
INSERT INTO activity VALUES (2, 2, 'T??tulo de atividade 2', 'Disciplina de atividade 2', 'Mat??ria de atividade 2', 'Enunciado da atividade 2');
INSERT INTO activity VALUES (3, 3, 'T??tulo de atividade 3', 'Disciplina de atividade 3', 'Mat??ria de atividade 3', 'Enunciado da atividade 3');
INSERT INTO activity VALUES (4, 4, 'T??tulo de atividade 4', 'Disciplina de atividade 4', 'Mat??ria de atividade 4', 'Enunciado da atividade 4');
INSERT INTO activity VALUES (5, 5, 'T??tulo de atividade 5', 'Disciplina de atividade 5', 'Mat??ria de atividade 5', 'Enunciado da atividade 5');
INSERT INTO activity VALUES (6, 6, 'T??tulo de atividade 6', 'Disciplina de atividade 6', 'Mat??ria de atividade 6', 'Enunciado da atividade 6');
INSERT INTO option VALUES (1, 1, 'Op????o errada 1 da atividade 1', FALSE);
INSERT INTO option VALUES (1, 2, 'Op????o errada 2 da atividade 1', FALSE);
INSERT INTO option VALUES (1, 3, 'Op????o correta da atividade 1', TRUE);
INSERT INTO option VALUES (1, 4, 'Op????o errada 3 da atividade 1', FALSE);
INSERT INTO option VALUES (2, 5, 'Op????o errada 1 da atividade 2', FALSE);
INSERT INTO option VALUES (2, 6, 'Op????o errada 2 da atividade 2', FALSE);
INSERT INTO option VALUES (2, 7, 'Op????o correta da atividade 2', TRUE);
INSERT INTO option VALUES (2, 8, 'Op????o errada 3 da atividade 2', FALSE);
INSERT INTO option VALUES (3, 9, 'Op????o errada 1 da atividade 3', FALSE);
INSERT INTO option VALUES (3, 10, 'Op????o errada 2 da atividade 3', FALSE);
INSERT INTO option VALUES (3, 11, 'Op????o correta da atividade 3', TRUE);
INSERT INTO option VALUES (3, 12, 'Op????o errada 3 da atividade 3', FALSE);
INSERT INTO option VALUES (4, 13, 'Op????o errada 1 da atividade 4', FALSE);
INSERT INTO option VALUES (4, 14, 'Op????o errada 2 da atividade 4', FALSE);
INSERT INTO option VALUES (4, 15, 'Op????o correta da atividade 4', TRUE);
INSERT INTO option VALUES (4, 16, 'Op????o errada 3 da atividade 4', FALSE);
INSERT INTO option VALUES (5, 17, 'Op????o errada 1 da atividade 5', FALSE);
INSERT INTO option VALUES (5, 18, 'Op????o errada 2 da atividade 5', FALSE);
INSERT INTO option VALUES (5, 19, 'Op????o correta da atividade 5', TRUE);
INSERT INTO option VALUES (5, 20, 'Op????o errada 3 da atividade 5', FALSE);
INSERT INTO option VALUES (6, 21, 'Op????o errada 1 da atividade 6', FALSE);
INSERT INTO option VALUES (6, 22, 'Op????o errada 2 da atividade 6', FALSE);
INSERT INTO option VALUES (6, 23, 'Op????o correta da atividade 6', TRUE);
INSERT INTO option VALUES (6, 24, 'Op????o errada 3 da atividade 6', FALSE);
INSERT INTO message VALUES (1, 7, 1, 'Assunto da mensagem 1', 'Corpo da mensagem 1', '2022-05-01');
INSERT INTO message VALUES (7, 1, 2, 'Assunto da mensagem 2', 'Corpo da mensagem 2', '2022-05-01');
INSERT INTO message VALUES (1, 2, 3, 'Assunto da mensagem 3', 'Corpo da mensagem 3', '2022-05-01');
INSERT INTO message VALUES (7, 1, 4, 'Assunto da mensagem 4', 'Corpo da mensagem 4', '2022-05-01');
INSERT INTO activity VALUES (1, 1000, 'Atividade n??mero 1000', 'Matem??tica', 'Subtra????o', 'Qual o resultado da subtra????o 47 - 26?', 2.5, 0, 0);
INSERT INTO option VALUES (1000, 4001, '21.0', TRUE);
INSERT INTO option VALUES (1000, 4002, '550.0', FALSE);
INSERT INTO option VALUES (1000, 4003, '616.0', FALSE);
INSERT INTO option VALUES (1000, 4004, '211.0', FALSE);
INSERT INTO activity VALUES (1, 1001, 'Atividade n??mero 1001', 'Matem??tica', 'Divis??o', 'Qual o resultado da divis??o 36 / 44?', 2.5, 0, 0);
INSERT INTO option VALUES (1001, 4005, '1332.0', FALSE);
INSERT INTO option VALUES (1001, 4006, '857.0', FALSE);
INSERT INTO option VALUES (1001, 4007, '0.8181818181818182', TRUE);
INSERT INTO option VALUES (1001, 4008, '310.0', FALSE);
INSERT INTO activity VALUES (1, 1002, 'Atividade n??mero 1002', 'Matem??tica', 'Multiplica????o', 'Qual o resultado da multiplica????o 23 * 50?', 2.5, 0, 0);
INSERT INTO option VALUES (1002, 4009, '1150.0', TRUE);
INSERT INTO option VALUES (1002, 4010, '262.0', FALSE);
INSERT INTO option VALUES (1002, 4011, '328.0', FALSE);
INSERT INTO option VALUES (1002, 4012, '325.0', FALSE);
INSERT INTO activity VALUES (1, 1003, 'Atividade n??mero 1003', 'Matem??tica', 'Multiplica????o', 'Qual o resultado da multiplica????o 20 * 12?', 2.5, 0, 0);
INSERT INTO option VALUES (1003, 4013, '240.0', TRUE);
INSERT INTO option VALUES (1003, 4014, '204.0', FALSE);
INSERT INTO option VALUES (1003, 4015, '122.0', FALSE);
INSERT INTO option VALUES (1003, 4016, '102.0', FALSE);
INSERT INTO activity VALUES (1, 1004, 'Atividade n??mero 1004', 'Matem??tica', 'Soma', 'Qual o resultado da soma 26 + 34?', 2.5, 0, 0);
INSERT INTO option VALUES (1004, 4017, '693.0', FALSE);
INSERT INTO option VALUES (1004, 4018, '81.0', FALSE);
INSERT INTO option VALUES (1004, 4019, '785.0', FALSE);
INSERT INTO option VALUES (1004, 4020, '60.0', TRUE);
INSERT INTO activity VALUES (1, 1005, 'Atividade n??mero 1005', 'Matem??tica', 'Soma', 'Qual o resultado da soma 30 + 5?', 2.5, 0, 0);
INSERT INTO option VALUES (1005, 4021, '51.0', FALSE);
INSERT INTO option VALUES (1005, 4022, '74.0', FALSE);
INSERT INTO option VALUES (1005, 4023, '116.0', FALSE);
INSERT INTO option VALUES (1005, 4024, '35.0', TRUE);
INSERT INTO activity VALUES (1, 1006, 'Atividade n??mero 1006', 'Matem??tica', 'Soma', 'Qual o resultado da soma 33 + 18?', 2.5, 0, 0);
INSERT INTO option VALUES (1006, 4025, '259.0', FALSE);
INSERT INTO option VALUES (1006, 4026, '146.0', FALSE);
INSERT INTO option VALUES (1006, 4027, '443.0', FALSE);
INSERT INTO option VALUES (1006, 4028, '51.0', TRUE);
INSERT INTO activity VALUES (1, 1007, 'Atividade n??mero 1007', 'Matem??tica', 'Subtra????o', 'Qual o resultado da subtra????o 58 - 14?', 2.5, 0, 0);
INSERT INTO option VALUES (1007, 4029, '593.0', FALSE);
INSERT INTO option VALUES (1007, 4030, '79.0', FALSE);
INSERT INTO option VALUES (1007, 4031, '683.0', FALSE);
INSERT INTO option VALUES (1007, 4032, '44.0', TRUE);
INSERT INTO activity VALUES (1, 1008, 'Atividade n??mero 1008', 'Matem??tica', 'Soma', 'Qual o resultado da soma 31 + 24?', 2.5, 0, 0);
INSERT INTO option VALUES (1008, 4033, '55.0', TRUE);
INSERT INTO option VALUES (1008, 4034, '447.0', FALSE);
INSERT INTO option VALUES (1008, 4035, '523.0', FALSE);
INSERT INTO option VALUES (1008, 4036, '439.0', FALSE);
INSERT INTO activity VALUES (1, 1009, 'Atividade n??mero 1009', 'Matem??tica', 'Multiplica????o', 'Qual o resultado da multiplica????o 44 * 26?', 2.5, 0, 0);
INSERT INTO option VALUES (1009, 4037, '504.0', FALSE);
INSERT INTO option VALUES (1009, 4038, '141.0', FALSE);
INSERT INTO option VALUES (1009, 4039, '1144.0', TRUE);
INSERT INTO option VALUES (1009, 4040, '1028.0', FALSE);
INSERT INTO activity VALUES (1, 1010, 'Atividade n??mero 1010', 'Matem??tica', 'Divis??o', 'Qual o resultado da divis??o 64 / 50?', 2.5, 0, 0);
INSERT INTO option VALUES (1010, 4041, '2497.0', FALSE);
INSERT INTO option VALUES (1010, 4042, '1.28', TRUE);
INSERT INTO option VALUES (1010, 4043, '197.0', FALSE);
INSERT INTO option VALUES (1010, 4044, '399.0', FALSE);
INSERT INTO activity VALUES (1, 1011, 'Atividade n??mero 1011', 'Matem??tica', 'Multiplica????o', 'Qual o resultado da multiplica????o 50 * 20?', 2.5, 0, 0);
INSERT INTO option VALUES (1011, 4045, '1000.0', TRUE);
INSERT INTO option VALUES (1011, 4046, '187.0', FALSE);
INSERT INTO option VALUES (1011, 4047, '775.0', FALSE);
INSERT INTO option VALUES (1011, 4048, '850.0', FALSE);
INSERT INTO activity VALUES (1, 1012, 'Atividade n??mero 1012', 'Matem??tica', 'Soma', 'Qual o resultado da soma 63 + 27?', 2.5, 0, 0);
INSERT INTO option VALUES (1012, 4049, '331.0', FALSE);
INSERT INTO option VALUES (1012, 4050, '635.0', FALSE);
INSERT INTO option VALUES (1012, 4051, '90.0', TRUE);
INSERT INTO option VALUES (1012, 4052, '1005.0', FALSE);
INSERT INTO activity VALUES (1, 1013, 'Atividade n??mero 1013', 'Matem??tica', 'Divis??o', 'Qual o resultado da divis??o 63 / 33?', 2.5, 0, 0);
INSERT INTO option VALUES (1013, 4053, '1.9090909090909092', TRUE);
INSERT INTO option VALUES (1013, 4054, '1859.0', FALSE);
INSERT INTO option VALUES (1013, 4055, '688.0', FALSE);
INSERT INTO option VALUES (1013, 4056, '1216.0', FALSE);
INSERT INTO activity VALUES (1, 1014, 'Atividade n??mero 1014', 'Matem??tica', 'Subtra????o', 'Qual o resultado da subtra????o 53 - 22?', 2.5, 0, 0);
INSERT INTO option VALUES (1014, 4057, '1027.0', FALSE);
INSERT INTO option VALUES (1014, 4058, '31.0', TRUE);
INSERT INTO option VALUES (1014, 4059, '216.0', FALSE);
INSERT INTO option VALUES (1014, 4060, '825.0', FALSE);

