package com.yuv.movieBooking.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.yuv.movieBooking.dto.UserDto;
import com.yuv.movieBooking.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	UserDto findByLoginId(String loginId);

	UserDto findByEmail(String email);

}
