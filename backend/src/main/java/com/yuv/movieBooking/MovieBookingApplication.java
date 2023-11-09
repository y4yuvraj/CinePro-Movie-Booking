package com.yuv.movieBooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import jakarta.annotation.Generated;

@SpringBootApplication
@ComponentScan(basePackages = "com.yuv.movieBooking")
@Generated(value = "Maven")
public class MovieBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieBookingApplication.class, args);
	}

}
