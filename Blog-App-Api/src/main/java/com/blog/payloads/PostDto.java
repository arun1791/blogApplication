package com.blog.payloads;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class PostDto {

	private String titile;
	private String content;
	
	private String imageName;
	private Date date;
	private CategoryDto category;
	private UserDto user;
}
