package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.exception.ResourcesNotoundExcpetion;
import com.blog.repository.UserRepository;

@Service
public class CustomeUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//loading user from user nmae
		User user = this.userRepository.findByEmail(username)
		.orElseThrow(()-> new ResourcesNotoundExcpetion("username", "email +:"+username,0));
		
		return user;
	}

}
