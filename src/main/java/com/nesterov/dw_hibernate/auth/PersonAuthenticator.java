package com.nesterov.dw_hibernate.auth;

import java.util.Optional;
import com.nesterov.dw_hibernate.model.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class PersonAuthenticator implements  Authenticator<BasicCredentials, User>{
	
	private String username;
	private String password;
	
	public PersonAuthenticator(String login, String password) {
		super();
		this.username = login;
		this.password = password;
	}

	

	@Override
	public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
		 if ("secret".equals(credentials.getPassword())) {
	            return Optional.of(new User());
	        }
	        return Optional.empty();
	}

	

}
