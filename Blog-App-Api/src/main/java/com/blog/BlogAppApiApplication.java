package com.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.AppConstant;
import com.blog.entities.Role;
import com.blog.repository.RoleRepsitory;


@SpringBootApplication
public class BlogAppApiApplication implements CommandLineRunner {
	@Autowired
	private  PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepsitory roleRepsitory;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApiApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(this.passwordEncoder.encode("9096032655"));
		try {
			Role role=new Role();
			role.setId(AppConstant.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role1=new Role();
			role1.setId(AppConstant.NORMAL_USER);
			role1.setName("ROLE_NORMAL");
			
			List<Role> roles = List.of(role,role1);
			
			List<Role> result = this.roleRepsitory.saveAll(roles);
			result.forEach(r->{
				System.out.println(r.getName());
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
