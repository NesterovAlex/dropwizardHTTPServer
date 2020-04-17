package com.nesterov.dw_hibernate.auth;

import com.nesterov.dw_hibernate.model.User;

import io.dropwizard.auth.Authorizer;

public class PersonAuthorizer implements Authorizer<User>{
	
	@Override
	public boolean authorize(User user, String role) {
		 return user.getName().equals("good-guy") && role.equals("ADMIN");
	}

}
