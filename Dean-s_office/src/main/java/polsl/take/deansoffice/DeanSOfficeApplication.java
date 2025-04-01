package polsl.take.deansoffice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DeanSOfficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeanSOfficeApplication.class, args);
	}

	@Bean
	public CommandLineRunner testRunner() {
		return args -> {
			System.out.println("App works propeprly!");
		};
	}

}

/*
 * SQL INSERT INTO USERS (USER_ID, EMAIL, END_DATE, IS_ACTIVE, NAME, PHONE,
 * START_DATE, SURNAME, APART_NUMBER, CITY, COUNTRY, POSTAL_CODE, STREET) VALUES
 * (1, 'test@gmail.com', NULL, 1, 'Michal', '123321123', '2022-12-12', 'Kozak',
 * '16A', 'Gliwice', 'Polska', '42-575', 'Akademicka'), (2, 'koks@gmail.com',
 * NULL, 1, 'Wojtek', '323232321', '2020-10-10', 'Mocarz', '17', 'Katowice',
 * 'Polska', '42-420', 'Mariacka'), (3, 'ddd@gmail.com', NULL, 1, 'Jarek',
 * '696969696', '1997-12-12', 'Poducha', '19', 'Sosnowiec', 'Polska', '32-333',
 * 'Wagowa'), (4, 'ggg@gmail.com', NULL, 1, 'Oleg', '666666666', '2013-01-01',
 * 'Antena', '202', 'Dupa', 'Niemcy', '77-878', 'Dunikowskiego');
 * 
 * INSERT INTO TEACHERS (DEPARTMENT, ROOM, TITLE, USER_ID) VALUES ('Kat.
 * Algorytmiki', '320', 'dr hab', 1), ('Kat. Grafiki', '321', 'dr', 4);
 * 
 * INSERT INTO STUDENTS (ALBUM, DEGREE, FIELD, SEMESTER, SPECIALIZATION,
 * USER_ID) VALUES ('wk420420', 'inz', 'Informatyka', '6', 'BDiIS1', 2),
 * ('gd420420', 'mgr', 'Informatyka', '6', 'BDiIS1', 3);
 * 
 * INSERT INTO SUBJECTS (SUBJECT_ID, NAME, MAIN_TEACHER_ID) VALUES (1, 'SMIW',
 * 1), (2, 'JA', 4);
 * 
 * INSERT INTO TEACHERS_SUBJECTS (TEACHER_SUBJECT_ID, SUBJECT_ID, TEACHER_ID)
 * VALUES (1, 1, 1), (2, 1, 4), (3, 2, 4);
 * 
 * INSERT INTO GRADES (GRADE_ID, FINAL_GRADE, STUDENT_ID, SUBJECT_ID) VALUES (1,
 * 5, 2, 1), (2, 2, 2, 2), (3, 3, 3, 1), (4, 4, 3, 2); SQL
 */