package pl.edu.pb.wi.project.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.pb.wi.project.database.models.User;

@SpringBootApplication
public class DatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseApplication.class, args);
		User user=new User();

	}
}
