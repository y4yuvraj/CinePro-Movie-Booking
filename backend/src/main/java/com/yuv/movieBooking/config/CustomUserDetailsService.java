package com.yuv.movieBooking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yuv.movieBooking.dto.UserDto;
import com.yuv.movieBooking.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		UserDto user = userRepo.findByLoginId(loginId);
		if (user != null) {
			UserDetails userDetails = new CustomUserDetails(user);
			return userDetails;
		}
		throw new UsernameNotFoundException("User not found for loginId: " + loginId);

	}
	
	

}
