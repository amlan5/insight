package com.api.insight.payloads;

import java.util.HashSet;
import java.util.Set;

import com.api.insight.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int userId;

	@NotEmpty
	@Size(min = 3, message = "Username should be minimum of 3 characters")
	private String name;

	@Email(message = "Email is invalid")
	private String email;

	@NotEmpty
	@Size(min = 5, max = 10, message = "Password should be minimum of 5 characters and maximum of 10 characters")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d\\s]).{5,14}$")
	private String password;

	@NotEmpty
	private String about;

	private RoleDto role;

}
