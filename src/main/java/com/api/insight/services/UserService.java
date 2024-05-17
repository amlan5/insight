package com.api.insight.services;

import java.util.List;

import com.api.insight.payloads.UserDto;

public interface UserService {
	
	UserDto registerUser(UserDto user);	

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUser(Integer userId);

	List<UserDto> getAllUsers();

	void deleteUser(Integer userId);
}
