package com.api.insight.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.insight.entities.User;
import com.api.insight.exceptions.ResourceNotFoundException;
import com.api.insight.repositories.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = this.userRepo.findByEmail(userEmail).orElseThrow(()-> new ResourceNotFoundException("User", "email "+userEmail, 0));
		return user;
	}

}
