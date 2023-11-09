package com.yuv.movieBooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yuv.movieBooking.dto.UserDto;
import com.yuv.movieBooking.entity.User;
import com.yuv.movieBooking.exception.UserAlreadyExistsException;
import com.yuv.movieBooking.exception.UserNotFoundException;
import com.yuv.movieBooking.service.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/moviebooking")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody @Valid UserDto userDto) throws UserAlreadyExistsException {
		return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
	}

	@GetMapping("/{loginId}/forgot")
	public ResponseEntity<String> forgotPassword(@PathVariable("loginId") String loginId) throws UserNotFoundException {
		UserDto user = userService.GetUserByLoginId(loginId);
		if (user != null && user.getRole().equalsIgnoreCase("ROLE_USER")) {
			String newPassword = userService.generateRandomPassword();
			user.setPassword(newPassword);
			userService.updateUserPassword(user);
			return new ResponseEntity<>(newPassword, HttpStatus.OK);
		} else {
			throw new UserNotFoundException("User not found for loginId: " + loginId);
		}

	}

	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	public UserServiceImpl getUserService() {
		return userService;
	}

}
