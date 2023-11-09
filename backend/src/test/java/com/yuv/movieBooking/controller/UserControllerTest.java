package com.yuv.movieBooking.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.yuv.movieBooking.dto.UserDto;
import com.yuv.movieBooking.entity.User;
import com.yuv.movieBooking.exception.UserAlreadyExistsException;
import com.yuv.movieBooking.exception.UserNotFoundException;
import com.yuv.movieBooking.service.UserServiceImpl;
import com.yuv.movieBooking.service.Impl.UserService;

class UserControllerTest {

	private UserController userController;
	private UserServiceImpl userService;

	@BeforeEach
	void setUp() {
		userService = mock(UserServiceImpl.class);
		userController = new UserController();
		userController.setUserService(userService);
	}

	@Test
	void testRegisterUser() throws UserAlreadyExistsException {
		UserDto userDto = new UserDto("testuser", "John", "Doe", "john@example.com", "password", "1234567890",
				"ROLE_USER");
		when(userService.addUser(userDto)).thenReturn(new User());

		ResponseEntity<User> response = userController.registerUser(userDto);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void testForgotPassword() throws UserNotFoundException {
		// Arrange
		String loginId = "testuser";
		UserDto userDto = new UserDto();
		userDto.setLoginId(loginId);
		userDto.setRole("ROLE_USER");
		when(userService.GetUserByLoginId(loginId)).thenReturn(userDto);
		when(userService.generateRandomPassword()).thenReturn("newpassword");

		ResponseEntity<String> response = userController.forgotPassword(loginId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("newpassword", response.getBody());
	}

	@Test
	void testForgotPasswordUserNotFound() {
		String loginId = "unknownUser";
		when(userService.GetUserByLoginId(loginId)).thenReturn(null);

		UserNotFoundException exception = assertThrows(UserNotFoundException.class,
				() -> userController.forgotPassword(loginId));
		assertEquals("User not found for loginId: unknownUser", exception.getMessage());
	}

}
