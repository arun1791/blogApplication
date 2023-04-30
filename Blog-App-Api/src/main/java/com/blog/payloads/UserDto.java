package com.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
	
	private int userId;
	@NotEmpty
	@Size(min = 4,message ="username must be minium  four chater")
	private String name;
	@Email(message = "youtr email id wrong ")
	private String email;
	@NotEmpty
	@Size(min = 3,max = 10, message = "password must be minimum 3 and max 10 char")
	//@Pattern(regexp = "")
	private String password;
	private String about;

}
