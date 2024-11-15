package com.flutter.alloffootball.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource({
	"classpath:application.properties",
	"classpath:application_admin.properties",
})
@SpringBootApplication(scanBasePackages = {
	"com.flutter.alloffootball.common",
	"com.flutter.alloffootball.admin"
})
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
