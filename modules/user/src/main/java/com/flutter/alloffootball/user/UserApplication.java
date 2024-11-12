package com.flutter.alloffootball.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource({
	"classpath:application.properties",
	"classpath:application_user.properties",
})
@SpringBootApplication(scanBasePackages = {
	"com.flutter.alloffootball.common",
	"com.flutter.alloffootball.user"
})
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
