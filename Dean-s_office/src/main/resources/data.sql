INSERT INTO USERS (USER_ID, ACTIVE, APART_NUMBER, CITY, COUNTRY, EMAIL, END_DATE, NAME, PHONE, POSTAL_CODE, START_DATE, STREET, SURNAME)
VALUES
(1, 1, '16A', 'Gliwice', 'Polska', 'Michal@test.com', NULL, 'Michał', '123321123', '42-575', '2022-12-12', 'Akademicka', 'Kozak'),
(2, 1, '17', 'Katowice', 'Polska', 'Wojtek@test.com', NULL, 'Wojtek', '323232321', '42-420', '2020-10-10', 'Mariacka', 'Mocarz'),
(3, 1, 'Z80', 'Sosnowiec', 'Polska', 'Jarek@test.com', NULL, 'Jarek', '696969696', '32-333', '1997-12-12', 'Wagowa', 'Poducha'),
(4, 1, '202', 'Będzin', 'Polska', 'Oleg@test.com', NULL, 'Oleg', '666666666', '77-878', '2013-01-01', 'Dunikowskiego', 'Antena'),
(5, 1, '8051', 'Będzin', 'Polska', 'Gabriel@test.com', NULL, 'Gabriel', '777777777', '69-333', '2000-12-01', 'Szosowa', 'Drobny'),
(6, 1, '123', 'Sosnowiec', 'Polska', 'MichalAndroid@test.com', NULL, 'Michal', '420420420', '42-575', '2000-03-08', 'Akademicka', 'Wielki'),
(7, 0, '5C', 'Będzin', 'Polska', 'Losowy@test.com', NULL, 'Rafał', '456456456', '88-333', '2015-07-07', 'Zielona', 'Losowy'); 

INSERT INTO TEACHERS (DEPARTMENT, ROOM, TITLE, USER_ID) 
VALUES
('Kat. Algorytmiki', '320', 'dr hab', 1),
('Kat. Grafiki', '321', 'dr', 4),
('Kat. Grafiki', '322', 'prof', 5);

INSERT INTO STUDENTS (ALBUM, DEGREE, FIELD, SEMESTER, SPECIALIZATION, USER_ID)
VALUES
('wm000111', 'inz', 'Informatyka', '6', 'BDiIS1', 2),
('jp000222', 'mgr', 'Informatyka', '8', 'IDiSI3', 3),
('mw000333', 'inz', 'Informatyka', '6', 'ISMiP', 6);

INSERT INTO SUBJECTS (SUBJECT_ID, NAME, MAIN_TEACHER_ID)
VALUES
(1, 'SMIW', 1),
(2, 'JA', 4),
(3, 'TM', 5);

INSERT INTO TEACHERS_SUBJECTS (TEACHER_SUBJECT_ID, SUBJECT_ID, TEACHER_ID)
VALUES
(1, 1, 1),
(2, 1, 4),
(3, 2, 4),
(4, 1, 5),
(5, 2, 5),
(6, 3, 5);

INSERT INTO GRADES (GRADE_ID, FINAL_GRADE, STUDENT_ID, SUBJECT_ID)
VALUES
(1, 3, 2, 1),
(2, 2, 2, 2),
(3, 3, 3, 1),
(4, 5, 6, 1),
(5, 4.5, 6, 2),
(6, 3.5, 6, 3);