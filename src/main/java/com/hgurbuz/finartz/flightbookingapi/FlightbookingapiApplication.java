package com.hgurbuz.finartz.flightbookingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class FlightbookingapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightbookingapiApplication.class, args);
	}

}
