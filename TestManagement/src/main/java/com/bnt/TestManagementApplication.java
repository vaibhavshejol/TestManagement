package com.bnt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class TestManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestManagementApplication.class, args);
		log.info("Application started successfully.....");
	}

}
