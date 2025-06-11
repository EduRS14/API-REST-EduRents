package com.ingsoft.tf.api_edurents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/* Descomentar la siguiente línea para usar la configuración de seguridad por defecto */
//@SpringBootApplication
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ApiEdurentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiEdurentsApplication.class, args);
	}

}
