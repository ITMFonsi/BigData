package at.fhv.itm2018.aufgabe4master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Aufgabe4masterApplication  extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Aufgabe4masterApplication.class, args);
	}
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Aufgabe4masterApplication.class);
    }
}
