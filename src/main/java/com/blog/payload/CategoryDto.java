package com.blog.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto {

	private Integer categoryId;

	@NotEmpty
	@Size(min = 4, message = "size must not be less than 4")
	private String categoryTitle;

//	@NotEmpty
	@Size(min = 10, message = "size must not be less than 10")
	private String categoryDescription;
}
