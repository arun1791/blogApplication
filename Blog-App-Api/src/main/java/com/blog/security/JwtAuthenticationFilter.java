package com.blog.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JWTTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		 String requestToken = request.getHeader("Authorization");
//		    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//		      return bearerToken.substring(7);
//		    }
//		    return null;
//		
//		String requestToken=request.getHeader("Authorization");
		//bearre token
		System.out.println("requestToken _____"+requestToken);
		String username=null;
		String token=null;
		if (StringUtils.hasText(requestToken) && requestToken.startsWith("Bearer "))
		{
			 token = requestToken.substring(7);
			 try {
				 username = this.jwtTokenHelper.getUsernameFromToken(token);
			} catch (Exception e) {
				
				System.out.println("uanble jwt token");
			}
			  
		}
		else
		{
			System.out.println("jwt token not begain with bearer");
		}
		
		
		//once we get the token validate
		
		if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userdetails = this.userDetailsService.loadUserByUsername(username);
			if(this.jwtTokenHelper.validateToken(token, userdetails))
			{
				//sahi chaalraja
				//authim karna 
				
				UsernamePasswordAuthenticationToken  usernamePasswordauthtoke=
						new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
				usernamePasswordauthtoke.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordauthtoke);
				
			}
			else 
			{
				System.out.println("invliad token");
			}
		}
		else
			
		{
			System.out.println("username is null and conetex is not null");
		}
		filterChain.doFilter(request, response);
		
	}

}
