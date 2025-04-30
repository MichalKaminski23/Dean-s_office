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
//I think everything is finished, the question is whether we leave the option to delete anything - change in Models, Services and Controllers.
