package com.abdulchakam.codinginterview;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Coding Interview V2", version = "1.0", description = "OpenAPI for coding interview v2 RESTFul API",
				contact = @Contact(name = "Muhammad Abdul Chakam", email = "muhabdulchakam@gmail.com", url = "https://github.com/abdulchakam/coding-interview-v2")))
public class CodingInterviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingInterviewApplication.class, args);
	}

}
