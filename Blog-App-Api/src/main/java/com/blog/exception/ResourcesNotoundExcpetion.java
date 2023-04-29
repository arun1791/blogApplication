package com.blog.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourcesNotoundExcpetion  extends RuntimeException{

	String resourceName;
	String feildName;
	long feildValue;
	public ResourcesNotoundExcpetion(String resourceName, String feildName, long feildValue) {
		super(String.format("%s not found with his name %s :%s", resourceName,feildName,feildValue));
		this.resourceName = resourceName;
		this.feildName = feildName;
		this.feildValue = feildValue;
	}
	
	
}
