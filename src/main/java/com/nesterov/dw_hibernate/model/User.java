package com.nesterov.dw_hibernate.model;

import java.security.Principal;

public class User implements Principal{
	
	private String name;
	
	@Override
	public String getName() {
		
		return name;
	}   
}
