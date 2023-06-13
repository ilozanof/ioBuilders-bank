package com.ioBuilders.bank.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author i.fernandez@nchain.com
 *
 * Spring REST Bank Application.
 * It only wires the "domain" and "infrastructure" modules up together and launches a Web Server.
 */
@SpringBootApplication
@ImportResource({"classpath*:applicationContext.xml"})
@ComponentScan(basePackages = "com.ioBuilders.bank")
@EntityScan("com.ioBuilders.bank.infrastructure")
public class BankApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}
}
