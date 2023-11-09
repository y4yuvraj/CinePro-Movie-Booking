package com.yuv.movieBooking.service.Impl;

import java.util.List;

import com.yuv.movieBooking.dto.UserDto;
import com.yuv.movieBooking.entity.User;
import com.yuv.movieBooking.exception.UserAlreadyExistsException;

public interface UserService {

	public User addUser(UserDto user) throws UserAlreadyExistsException;

	public List<User> allUsers();

	public UserDto GetUserByLoginId(String loginId);

	public String generateRandomPassword();

	public User updateUserPassword(UserDto userDto);

}
