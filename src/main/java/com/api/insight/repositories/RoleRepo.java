package com.api.insight.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.insight.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

	
}
