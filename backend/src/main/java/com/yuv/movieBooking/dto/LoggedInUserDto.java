package com.yuv.movieBooking.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.yuv.movieBooking.config.CustomUserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggedInUserDto {
	
	private String loginId;
	private String firstName;
	private String lastName;
	private String email;
	private String contactNumber;
	private Collection<? extends GrantedAuthority> authorities;
	private Boolean enabled;
	private Boolean credentialsNonExpired;
	private Boolean accountNonExpired;
	
	
	public LoggedInUserDto(CustomUserDetails userDetails)
	{
		this.firstName=userDetails.getFirstName();
		this.lastName=userDetails.getLastName();
		this.email=userDetails.getEmail();
		this.contactNumber=userDetails.getContactNumber();
		this.authorities=userDetails.getAuthorities();
		this.enabled=userDetails.isEnabled();
		this.loginId=userDetails.getUsername();
		this.enabled=userDetails.isAccountNonLocked();
		this.credentialsNonExpired=userDetails.isCredentialsNonExpired();
		this.accountNonExpired=userDetails.isAccountNonExpired();
	}
	
}
