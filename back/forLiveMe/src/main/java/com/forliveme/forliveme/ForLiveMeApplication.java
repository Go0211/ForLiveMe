package com.forliveme.forliveme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ForLiveMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForLiveMeApplication.class, args);
	}

}
