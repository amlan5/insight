package com.api.insight.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.insight.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
