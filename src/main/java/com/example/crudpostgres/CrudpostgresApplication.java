package com.example.crudpostgres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CrudpostgresApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudpostgresApplication.class, args);
	}
	
	@GetMapping("/")
	public String index()
	{
		return String.format("Application successfully working");
	}

}
