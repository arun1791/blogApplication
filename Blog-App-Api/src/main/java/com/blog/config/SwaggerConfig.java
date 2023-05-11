package com.blog.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@EnableSwagger2
public class SwaggerConfig {
	public static final String  AUTHORIZATION_HEADER="Authorization";
	
	private ApiKey apiKeys()
	{
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}
	
	private List<SecurityContext> securityContexts()
	{
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
	}
	
	private List<SecurityReference>securityReferences(){
		AuthorizationScope scopes=new AuthorizationScope("global", "access eberything");
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scopes}));
		
	}
	@Bean
	public Docket api()
	{
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis((RequestHandlerSelectors.any()))
				.paths(PathSelectors.any())
				.build();
				
	}

	private ApiInfo getInfo() {
		
		
		return new ApiInfo(" blogging backedn coures", "this is project develope ny learn with durgesh", 
				"1.0", "term and service", new Contact("Arun", "https://matrupeeth.in", "arun@123gmail.com"), 
				"leaince of api ", "api laince url",Collections.emptyList());
	}

}
