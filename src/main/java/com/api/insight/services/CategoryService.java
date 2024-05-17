package com.api.insight.services;

import java.util.List;

import com.api.insight.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);

	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	CategoryDto getCategory(Integer categoryId);

	List<CategoryDto> getAllCategories();

	void deleteCategory(Integer categoryId);
}
