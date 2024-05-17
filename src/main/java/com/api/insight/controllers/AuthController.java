package com.api.insight.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.insight.payloads.JwtAuthRequest;
import com.api.insight.payloads.UserDto;
import com.api.insight.security.JwtAuthResponse;
import com.api.insight.security.JwtTokenHelper;
import com.api.insight.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/insight/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenhelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request){
		this.authenticate(request.getEmail(),request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
		String token = this.jwtTokenhelper.generateToken(userDetails);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUser(@Valid @RequestBody UserDto userDto) {
		UserDto newUser = this.userService.registerUser(userDto);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}

	private void authenticate(String email, String password) {
		// TODO Auto-generated method stub
		UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(email, password);
		this.authenticationManager.authenticate(usernamePassword);
	}
}
