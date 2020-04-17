package com.nesterov.dw_hibernate.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.sym.Name3;
import com.nesterov.dw_hibernate.db.Names;
import com.nesterov.dw_hibernate.db.PersonDAO;
import com.nesterov.dw_hibernate.model.Person;
import com.nesterov.dw_hibernate.model.User;
import com.nesterov.dw_hibernate.views.PersonViews;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/v1")
@Timed
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.TEXT_HTML)
public class PersonResource {
	
	private PersonDAO personDAO;
	private Names names;
	
	
	
public PersonResource(PersonDAO personDAO, Names names) {
		super();
		this.personDAO = personDAO;
		this.names = names;
	}



	
	@GET
	@Path("/query")
	public String query(@QueryParam("name") String name, @QueryParam("age") int age) {
		return "name:" + name + "  age:" + age;
	}
	
	  @PUT
	  @Path("/postparam")
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public String postParam(@FormParam("name") String name) {
	    return "You posted " + name;
	  }
	
	@GET
	@Path("/name")
	@UnitOfWork
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String[]> getAllPerson(){
		Map<String, String[]> mapOfNames = new HashMap<String, String[]>();
		List namesList = names.namesList();
		String[] nameArr = new String[namesList.size()];
		for (int i = 0; i < nameArr.length; i++) {
			nameArr[i] = String.valueOf(namesList.get(i));
			}
		mapOfNames.put("Names", nameArr);
		return  mapOfNames;
	}
	
	@PUT
	@Path("/name")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@UnitOfWork
	public Response save(
			@FormParam("name") @NotEmpty String name,
			@FormParam("age") @NotEmpty int age
			) throws URISyntaxException {
		int newAge = age+1;
		Person newPerson = new Person(name);
		newPerson = personDAO.create(newPerson);
//		URI location = UriBuilder.fromUri("/v1/name").build();
//		return Response.status(400).entity("Badly formated").build(); 
		return Response.accepted("Hello " + name + ". Next year you will be " + newAge).status(200).build();
	}	
	
	 @GET
	 @Path("/Auth")
	 @Produces(MediaType.TEXT_PLAIN)
	 public String getGreeting(@Auth User user) {
	     return "Hello world!";
	 }
}