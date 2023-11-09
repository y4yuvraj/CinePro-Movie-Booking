package com.yuv.movieBooking.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.yuv.movieBooking.dto.UserDto;
import com.yuv.movieBooking.entity.User;

@DataMongoTest
@TestInstance(Lifecycle.PER_CLASS)
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	private User user;

	@BeforeAll
	void setUp() {

		user = new User("123", "yuv", "singh", "abc@example.com", "1234", "1234567890", "ROLE_USER");
		userRepository.save(user);
	}

	@Test
	void testFindByLoginId() {
		UserDto foundUser = userRepository.findByLoginId("123");
		assertNotNull(foundUser);
		assertEquals("123", foundUser.getLoginId());
	}

	@Test
	void testFindByEmail() {
		UserDto foundUsers = userRepository.findByEmail("abc@example.com");
		assertNotNull(foundUsers);
		assertEquals("yuv", foundUsers.getFirstName());
	}

	@AfterAll
	void tearDown() {
		userRepository.delete(user);
	}

}
