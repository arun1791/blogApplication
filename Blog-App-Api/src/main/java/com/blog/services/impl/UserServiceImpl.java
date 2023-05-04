package com.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstant;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exception.ResourcesNotoundExcpetion;
import com.blog.payloads.UserDto;
import com.blog.repository.RoleRepsitory;
import com.blog.repository.UserRepository;
import com.blog.services.UserService;

@Service
public class UserServiceImpl  implements UserService{
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepsitory roleRepsitory;

	@Override
	public UserDto createuser(UserDto userDto) {
		
		User user=this.dtoToUser(userDto);
		User saveUsers=this.userRepository.save(user);
		return this.userToDto(saveUsers);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user=this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourcesNotoundExcpetion("User","Id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updateUser=this.userRepository.save(user);
		UserDto userDto1= this.userToDto(updateUser);
		return userDto1;
		
		
	}

	@Override
	public UserDto getByuserId(Integer userId) {
			
		User user=this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourcesNotoundExcpetion("User","Id",userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {

		List<User> users=this.userRepository.findAll();
		List<UserDto> userDtos=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User	user =this.userRepository.findById(userId).orElseThrow(()-> new ResourcesNotoundExcpetion("User","Id",userId));
		this.userRepository.delete(user);
	}
	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.mapper.map(userDto, User.class);
		//endoed the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		//roles
		Role role = this.roleRepsitory.findById(AppConstant.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser=this.userRepository.save(user);
		return this.mapper.map(newUser, UserDto.class);
	}

	
	private User dtoToUser(UserDto userDto)
	{
		User user=this.mapper.map(userDto, User.class);
//		user.setUserId(userDto.getUserId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		return user;
		
	}
	
	public UserDto userToDto (User user)
	{
		UserDto userDto=this.mapper.map(user, UserDto.class);
//		userDto.setUserId(user.getUserId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		return userDto;
			
	}

	
}
