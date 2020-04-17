package com.nesterov.dw_hibernate.db;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.nesterov.dw_hibernate.model.Person;

import io.dropwizard.hibernate.AbstractDAO;

public class PersonDAO extends AbstractDAO<Person>{

	public PersonDAO(SessionFactory sessionFactory) {
		super(sessionFactory); 
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Person>findAll(){
		return (List<Person>) currentSession().createCriteria(Person.class).list();
	}
	
	 public List<Person> getByName(String name){
	        StringBuilder builder = new StringBuilder("%");
	        builder.append(name).append("%");
	        return list((Criteria) namedQuery("com.nesterov.dw_hibernate.db.person.GetByName").setParameter("name", builder.toString()));
	    }
	 
	 protected org.hibernate.query.Query<?> namedQuery(String queryName) throws HibernateException {
	        return currentSession().getNamedQuery("");
	    }
	 
	 public Person create(Person person) {
	        return persist(person);
	    }

}
