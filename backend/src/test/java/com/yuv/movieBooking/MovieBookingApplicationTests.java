package com.yuv.movieBooking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.constraints.AssertTrue;

@SpringBootTest
class MovieBookingApplicationTests {

	@Test
	void contextLoads() {
		String res = "test!";
		assertEquals("test!", res);
	}

}
