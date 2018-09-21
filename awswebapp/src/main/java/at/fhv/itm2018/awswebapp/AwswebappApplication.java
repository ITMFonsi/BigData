package at.fhv.itm2018.awswebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AwswebappApplication {

	public static void main(String[] args) {

		User user = new User("fonsi", "fons");


		SpringApplication.run(AwswebappApplication.class, args);
	}
}
