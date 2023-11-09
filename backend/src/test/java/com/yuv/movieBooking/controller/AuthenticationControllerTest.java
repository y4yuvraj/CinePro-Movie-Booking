package com.yuv.movieBooking.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.yuv.movieBooking.config.CustomUserDetails;
import com.yuv.movieBooking.config.CustomUserDetailsService;
import com.yuv.movieBooking.dto.LoggedInUserDto;
import com.yuv.movieBooking.exception.CustomException;

class AuthenticationControllerTest {

	private AuthenticationController authenticationController;
	private CustomUserDetailsService customUserDetailsService;

	@BeforeEach
	void setUp() {
		customUserDetailsService = mock(CustomUserDetailsService.class);
		authenticationController = new AuthenticationController(customUserDetailsService);

	}

	@Test
	void testGetLoggedInUser() throws CustomException {
		Principal principal = () -> "testUser";
		CustomUserDetails userDetails = new CustomUserDetails("testUser", "password", null);
		when(customUserDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);

		ResponseEntity<LoggedInUserDto> response = authenticationController.getLoggedInUser(principal);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("testUser", response.getBody().getLoginId());
	}

	@Test
	void testGetLoggedInUserNotFound() {
		Principal principal = () -> "unknownUser";
		when(customUserDetailsService.loadUserByUsername("unknownUser"))
				.thenThrow(new UsernameNotFoundException("User not found"));

		CustomException customException = assertThrows(CustomException.class,
				() -> authenticationController.getLoggedInUser(principal));
		assertEquals("username not found", customException.getMessage());
	}

}
