package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.exception.ApiException;
import com.blog.payloads.JWTAuthRequest;
import com.blog.payloads.JWTAuthResponse;
import com.blog.payloads.UserDto;
import com.blog.security.JWTTokenHelper;
import com.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> creatToken(
			@RequestBody JWTAuthRequest jwtAuthRequest
			) throws Exception
	{
		
		this.authenticate(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword());
		
		UserDetails userdeatils = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
		String token = this.jwtTokenHelper.generateToken(userdeatils);
		JWTAuthResponse response=new JWTAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JWTAuthResponse>(response,HttpStatus.OK);
		
	}

	private void authenticate(String username, String password) throws Exception {
		
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
					new UsernamePasswordAuthenticationToken(username, password);
			try {
				this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			} catch (BadCredentialsException e) {
			System.out.println("useris not found");
				throw new ApiException("Invalid username and password !!");
			
			}
	}
	
	//regsiter mw user
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto)
	{
		UserDto regsitedUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(regsitedUser,HttpStatus.CREATED);
		
	}

}
