package com.yuv.movieBooking.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.yuv.movieBooking.dto.UserDto;
import com.yuv.movieBooking.entity.User;
import com.yuv.movieBooking.exception.UserAlreadyExistsException;
import com.yuv.movieBooking.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;

	private UserServiceImpl userServiceImpl;

	@BeforeEach
	void setUp() {
		this.userServiceImpl = new UserServiceImpl(userRepository, passwordEncoder); 
	}

	@Test
	void testAddUser_UserAlreadyExistsUsingEmail() { 
		UserDto userDto = new UserDto("someuser","John","Doe","some@example.com","password","1234567890","ROLE_USER");
		when(userRepository.findByEmail("some@example.com")).thenReturn(new UserDto());
		assertThrows(UserAlreadyExistsException.class, () -> userServiceImpl.addUser(userDto));
		verify(userRepository, times(1)).findByEmail("some@example.com");
	}

	@Test
	void testAddUser_UserAlreadyExistsUsingLoginId() { 
		UserDto userDto = new UserDto("someuser","John","Doe","some@example.com","password","1234567890","ROLE_USER");
		when(userRepository.findByLoginId("someuser")).thenReturn(new UserDto());
		assertThrows(UserAlreadyExistsException.class, () -> userServiceImpl.addUser(userDto));
		verify(userRepository, times(1)).findByLoginId("someuser");
	}

	
	@Test
	void testAddUser() throws UserAlreadyExistsException {
		UserDto userDto = new UserDto("testuser","John","Doe","john@example.com","password","1234567890","ROLE_USER");
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(userRepository.findByLoginId(anyString())).thenReturn(null);
		when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
		User addedUser = userServiceImpl.addUser(userDto);
		verify(userRepository, times(1)).save(any(User.class));
		assertNotNull(addedUser);
		assertEquals("testuser", addedUser.getLoginId());
		assertEquals("John", addedUser.getFirstName());
		assertEquals("Doe", addedUser.getLastName());
		assertEquals("john@example.com", addedUser.getEmail());
		assertEquals("encodedPassword", addedUser.getPassword());
		assertEquals("1234567890", addedUser.getContactNumber());
		assertEquals("ROLE_USER", addedUser.getRole());
	}

	@Test
	void testAllUsers() {
		   when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
		    List<User> users = userServiceImpl.allUsers();
		    verify(userRepository, times(1)).findAll();
		    assertNotNull(users);
		    assertEquals(2, users.size());
	}

	@Test
	void testGetUserByLoginId() {
	    when(userRepository.findByLoginId(anyString())).thenReturn(new UserDto("testuser", "John", "Doe", "john@example.com", "password", "1234567890", "ROLE_USER"));
	    UserDto user = userServiceImpl.GetUserByLoginId("testuser");
	    verify(userRepository, times(1)).findByLoginId("testuser");
	    assertNotNull(user);
	    assertEquals("testuser", user.getLoginId());
	    assertEquals("John", user.getFirstName());
	    assertEquals("Doe", user.getLastName());
	    assertEquals("john@example.com", user.getEmail());
	    assertEquals("password", user.getPassword());
	    assertEquals("1234567890", user.getContactNumber());
	    assertEquals("ROLE_USER", user.getRole());
	}

	@Test
	void testGenerateRandomPassword() {
		String password = userServiceImpl.generateRandomPassword();
		assertNotNull(password);
		assertEquals(8, password.length());
	}

	@Test
	void testUpdateUserPassword() {
		UserDto userDto = new UserDto("testuser", "John", "Doe", "john@example.com", "password", "1234567890", "ROLE_USER");
		when(passwordEncoder.encode(anyString())).thenReturn("encodedNewPassword");
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));	
		User updatedUser = userServiceImpl.updateUserPassword(userDto);
		verify(userRepository, times(1)).save(any(User.class));
		assertNotNull(updatedUser);
		assertEquals("testuser", updatedUser.getLoginId());
		assertEquals("John", updatedUser.getFirstName());
		assertEquals("Doe", updatedUser.getLastName());
		assertEquals("john@example.com", updatedUser.getEmail());
		assertEquals("encodedNewPassword", updatedUser.getPassword());
		assertEquals("1234567890", updatedUser.getContactNumber());
		assertEquals("ROLE_USER", updatedUser.getRole());
	}

}
