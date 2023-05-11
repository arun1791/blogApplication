package com.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blog.entities.Role;

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
	private Set<Role> roles=new HashSet<>();

}
