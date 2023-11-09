package com.yuv.movieBooking.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.yuv.movieBooking.dto.UserDto;

public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String loginId;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(UserDto user) {
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.contactNumber = user.getContactNumber();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

    public CustomUserDetails(String string, String string2, List<GrantedAuthority> emptySet) {
		// TODO Auto-generated constructor stub
    	this.loginId=string;
    	this.password=string2;
    	this.authorities=emptySet;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return loginId;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}