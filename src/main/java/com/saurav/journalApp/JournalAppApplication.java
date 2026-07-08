package com.saurav.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalAppApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(JournalAppApplication.class, args);
		ConfigurableEnvironment environment = context.getEnvironment();
		String[] profiles = environment.getActiveProfiles();
		String activeProfile = profiles.length > 0 ? profiles[0] : "default";
		String port = environment.getProperty("local.server.port", environment.getProperty("server.port", "unknown"));
		System.out.println("==============================================================================================");
		System.out.println("APPLICATION HAS STARTED SUCCESSFULLY ON PROFILE ='" + activeProfile + "' PORT='" + port + "'");
		System.out.println("==============================================================================================");
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
