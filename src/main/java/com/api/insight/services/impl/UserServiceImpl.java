package com.api.insight.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.insight.config.AppConstants;
import com.api.insight.entities.Role;
import com.api.insight.entities.User;
import com.api.insight.payloads.UserDto;
import com.api.insight.repositories.RoleRepo;
import com.api.insight.repositories.UserRepo;
import com.api.insight.services.UserService;
import com.api.insight.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;


	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub

		User user = this.dtoToUser(userDto);
		User savedUser = userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User savedUser = this.userRepo.save(user);

		return this.userToDto(savedUser);
	}

	@Override
	public UserDto getUser(Integer userId) {
		// TODO Auto-generated method stub

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub

		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		this.userRepo.delete(user);

	}

	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}

	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user = this.dtoToUser(userDto);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		//user.getRoles().add(role);
		user.setRole(role);
		User savedUser = userRepo.save(user);
		return this.modelMapper.map(savedUser,UserDto.class);
	}

//	private User dtoToUser(UserDto userDto) {
//		User user = new User();
//		user.setUser_id(userDto.getUser_id());
//		user.setUser_name(userDto.getUser_name());
//		user.setUser_email(userDto.getUser_email());
//		user.setUser_password(userDto.getUser_password());
//		user.setUser_about(userDto.getUser_about());
//		return user;
//	}
//
//	private UserDto userToDto(User user) {
//		UserDto userDto = new UserDto();
//		userDto.setUser_id(user.getUser_id());
//		userDto.setUser_name(user.getUser_name());
//		userDto.setUser_email(user.getUser_email());
//		userDto.setUser_password(user.getUser_password());
//		userDto.setUser_about(user.getUser_about());
//		return userDto;
//	}

}
