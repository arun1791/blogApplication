package com.blog.services;

import java.util.List;

import com.blog.payloads.UserDto;

public interface UserService {
	
	UserDto createuser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getByuserId(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);

}
