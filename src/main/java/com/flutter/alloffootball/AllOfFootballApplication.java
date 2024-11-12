package com.flutter.alloffootball;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // BaseEntityTime,BaseEntityImage 객체 활성화
@SpringBootApplication
public class AllOfFootballApplication {

	public static void main(String[] args) {
		SpringApplication.run(AllOfFootballApplication.class, args);
	}

}
