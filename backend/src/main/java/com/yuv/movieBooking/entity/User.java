package com.yuv.movieBooking.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor(staticName = "build")
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

	@Id
	private String loginId;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String contactNumber;

	private String role = "ROLE_USER";

}
