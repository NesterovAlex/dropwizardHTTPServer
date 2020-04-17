package com.nesterov.dw_hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "names")
public class Person {
	
	@Id
	@Column(name="name")
	String name;
	
	public Person() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person( String name) {
		super();
		this.name = name;
	}
	
	
}
