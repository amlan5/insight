package com.api.insight;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.api.insight.config.AppConstants;
import com.api.insight.entities.Role;
import com.api.insight.repositories.RoleRepo;

@SpringBootApplication
public class InsightApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(InsightApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(this.passwordEncoder.encode("NHlyX@123I"));

		try {
			Role role = new Role();
			role.setRoleId(AppConstants.ADMIN_USER);
			role.setRoleName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setRoleId(AppConstants.NORMAL_USER);
			role1.setRoleName("ROLE_USER");

			List<Role> roles = List.of(role, role1);
			List<Role> result = this.roleRepo.saveAll(roles);

			result.forEach(r -> {
				System.out.println(r.getRoleName());
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
