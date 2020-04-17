package com.nesterov.dw_hibernate;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.constraints.NotEmpty;
import io.dropwizard.db.DataSourceFactory;

public class AppConfiguration extends Configuration {
	 

    /**
     * User login.
     */
    @NotEmpty
    private String login;
    /**
     * User password.
     */
    @NotEmpty
    private String password;
    /**
     * The URL to access exchange rate API.
     */
    @NotEmpty
    private String apiURL;
    /**
     * The key to access exchange rate API.
     */
    @NotEmpty
    private String apiKey;
    
    
	
	 public String getLogin() {
		return login;
	}


	public String getPassword() {
		return password;
	}


	public String getApiURL() {
		return apiURL;
	}


	public String getApiKey() {
		return apiKey;
	}


	public DataSourceFactory getDatabase() {
		return database;
	}

	
	@Valid
	 @NotNull
	 private DataSourceFactory database = new DataSourceFactory();
	 
	  @JsonProperty("database")	    
	  public DataSourceFactory getDataSourceFactory() {
		  return database;
	  }

	  @JsonProperty("database")
	  public void setDataSourceFactory(DataSourceFactory database) {
		  this.database = database;
	  }
	  
}
