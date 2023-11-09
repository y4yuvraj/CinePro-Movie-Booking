package com.yuv.movieBooking.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yuv.movieBooking.config.CustomUserDetails;
import com.yuv.movieBooking.config.CustomUserDetailsService;
import com.yuv.movieBooking.config.JwtUtils;
import com.yuv.movieBooking.dto.LoggedInUserDto;
import com.yuv.movieBooking.dto.LoginDto;
import com.yuv.movieBooking.exception.CustomException;
import com.yuv.movieBooking.service.UserServiceImpl;

@CrossOrigin("*")
@RestController
public class AuthenticationController {

	@Autowired
	private JwtUtils jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	UserServiceImpl userService;

	public AuthenticationController(CustomUserDetailsService customUserDetailsService2) {
		// TODO Auto-generated constructor stub
		this.customUserDetailsService = customUserDetailsService2;
	}

	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<String> authenticateAndGetToken(@RequestBody LoginDto loginIdAndPassword) {

		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginIdAndPassword.getLoginId(), loginIdAndPassword.getPassword()));
		if (auth.isAuthenticated()) {
			return new ResponseEntity<>(jwtService.generateToken(loginIdAndPassword.getLoginId()), HttpStatus.OK);
		} else {
			throw new UsernameNotFoundException("invalid user request");
		}
	}

	@GetMapping("/current-user")
	public ResponseEntity<LoggedInUserDto> getLoggedInUser(Principal principal) throws CustomException {
		UserDetails userDetails;
		try {
			userDetails = this.customUserDetailsService.loadUserByUsername(principal.getName());

		} catch (Exception e) {
			// Handle the case where the user is not found
			throw new CustomException("username not found");
		}
		return new ResponseEntity<>(new LoggedInUserDto((CustomUserDetails) userDetails), HttpStatus.OK);

	}

}
