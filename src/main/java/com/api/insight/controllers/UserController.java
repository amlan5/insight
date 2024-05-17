package com.api.insight.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.insight.payloads.ApiResponse;
import com.api.insight.payloads.UserDto;
import com.api.insight.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/insight")
public class UserController {

	@Autowired
	private UserService userService;

	// POST
	@PostMapping("/user")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
	}

	// PUT
	@PutMapping("/user/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
		UserDto updatedUserDto = this.userService.updateUser(userDto, userId);
		return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
	}

	// DELETE
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/user/{userId}")
	// public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
		this.userService.deleteUser(userId);
		// return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
		return ResponseEntity.ok(new ApiResponse("User deleted successfully", true));
	}

	// GET
	@GetMapping("/user")
	public ResponseEntity<List<UserDto>> getAllUsers() {
//		List<UserDto> userDtos = this.userService.getAllUsers();
//		return new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);

		return ResponseEntity.ok(this.userService.getAllUsers());
	}

	// GET
	@GetMapping("/user/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userId") Integer uid) {
		return ResponseEntity.ok(this.userService.getUser(uid));
	}
}
