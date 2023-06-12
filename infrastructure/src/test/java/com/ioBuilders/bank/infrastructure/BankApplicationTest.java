package com.ioBuilders.bank.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:applicationContext.xml"})
@ComponentScan(basePackages = "com.ioBuilders.bank")
@EntityScan("com.ioBuilders.bank.infrastructure")
public class BankApplicationTest {
	public static void main(String[] args) {
		SpringApplication.run(BankApplicationTest.class, args);
	}
}
