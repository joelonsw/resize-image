package com.example.resizeimage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ResizeImageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResizeImageApplication.class, args);
	}

}
