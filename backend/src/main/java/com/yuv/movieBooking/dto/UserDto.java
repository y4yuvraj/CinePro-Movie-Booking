package com.yuv.movieBooking.dto;

import org.springframework.data.mongodb.core.index.Indexed;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	@Indexed(unique = true)
	@NotNull(message = "Login Id is mandatory")
	@NotBlank(message = "Login Id is mandatory")
    private String loginId;
    
	@NotNull(message = "First Name is mandatory")
	@NotBlank(message = "First Name is mandatory")
    private String firstName;

	@NotNull(message = "Last Name is mandatory")
	@NotBlank(message = "Last Name is mandatory")
    private String lastName;

    @Email(message = "Email should be valid")
    @Indexed(unique = true)
    @NotNull(message = "Email is mandatory")
	@NotBlank(message = "Email is mandatory")
    private String email;
    
    @NotBlank(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Contact Number is mandatory")
    @NotNull(message = "Contact Number is mandatory")
    @Pattern(regexp =  "^\\d{10}$",message = "not a valid number")
    private String contactNumber;
    
    private String role = "ROLE_USER";

 
}
