package com.aplicacionesjc.agenda_contactos_appv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@SpringBootApplication
public class AgendaContactosAppv2Application {

	public static void main(String[] args) {
		SpringApplication.run(AgendaContactosAppv2Application.class, args);
	}

	@Bean
	public SpringDataDialect springDataDialect() {
		return new SpringDataDialect();
	}

}
