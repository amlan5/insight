package com.api.insight;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.api.insight.repositories.UserRepo;
import com.api.insight.services.UserService;

@SpringBootTest
class InsightApplicationTests {
	
	@Autowired
	UserRepo userRepo;
	@Autowired
	UserService userService;

	@Test
	void contextLoads() {
	}
	
	@Test
	void repoTest() {
		System.out.println(this.userRepo.getClass().getName());
		System.out.println(this.userService.getClass().getName());
		System.out.println(this.userService.getClass().getPackageName());
	}

}
