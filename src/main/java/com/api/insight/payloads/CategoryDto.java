package com.api.insight.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private int categoryId;

	@NotEmpty
	@Size(min = 3, message = "Categoryname should be minimum of 3 characters")
	private String categoryName;

	@NotEmpty
	private String categoryDescription;

}
