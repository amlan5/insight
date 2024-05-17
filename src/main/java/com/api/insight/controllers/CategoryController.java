package com.api.insight.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.insight.payloads.ApiResponse;
import com.api.insight.payloads.CategoryDto;
import com.api.insight.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/insight")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/category")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categorydto) {
		CategoryDto createdCategorydto = this.categoryService.createCategory(categorydto);
		return new ResponseEntity<>(createdCategorydto, HttpStatus.CREATED);
	}

	@PutMapping("/category/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categorydto,
			@PathVariable Integer categoryId) {
		CategoryDto updatedCategorydto = this.categoryService.updateCategory(categorydto, categoryId);
		return ResponseEntity.ok(updatedCategorydto);
	}

	@DeleteMapping("/category/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);
	}

	@GetMapping("/category/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
		return ResponseEntity.ok(this.categoryService.getCategory(categoryId));
	}

	@GetMapping("/category")
	public ResponseEntity<List<CategoryDto>> getAllCategories() {
		return ResponseEntity.ok(this.categoryService.getAllCategories());
	}
}
