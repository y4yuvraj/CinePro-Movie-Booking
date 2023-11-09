package com.yuv.movieBooking.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yuv.movieBooking.dto.UserDto;
import com.yuv.movieBooking.entity.User;
import com.yuv.movieBooking.exception.UserAlreadyExistsException;
import com.yuv.movieBooking.repository.UserRepository;
import com.yuv.movieBooking.service.Impl.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User addUser(UserDto userDto) throws UserAlreadyExistsException {

		if (userRepository.findByEmail(userDto.getEmail()) != null) {
			throw new UserAlreadyExistsException("Email already exists.");
		}

		if (userRepository.findByLoginId(userDto.getLoginId()) != null) {
			throw new UserAlreadyExistsException("LoginId already exists.");
		}

		User user = new User(userDto.getLoginId(), userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),
				passwordEncoder.encode(userDto.getPassword()), userDto.getContactNumber(), userDto.getRole());

		return userRepository.save(user);
	}

	@Override
	public List<User> allUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserDto GetUserByLoginId(String loginId) {
		// TODO Auto-generated method stub
		UserDto user = userRepository.findByLoginId(loginId);
		return user;
	}

	@Override
	public String generateRandomPassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder password = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
			password.append(characters.charAt(random.nextInt(characters.length())));
		}
		return password.toString();
	}

	@Override
	public User updateUserPassword(UserDto userDto) {
		// TODO Auto-generated method stub
//		System.out.println(userDto);
		User user = new User(userDto.getLoginId(), userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(),
				passwordEncoder.encode(userDto.getPassword()), userDto.getContactNumber(), userDto.getRole());
		return userRepository.save(user);
	}

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

}
