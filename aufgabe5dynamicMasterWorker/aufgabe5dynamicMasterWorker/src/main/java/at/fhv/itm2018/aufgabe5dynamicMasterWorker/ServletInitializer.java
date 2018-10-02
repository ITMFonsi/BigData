package at.fhv.itm2018.aufgabe5dynamicMasterWorker;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Aufgabe5dynamicMasterWorkerApplication.class);
	}

}
