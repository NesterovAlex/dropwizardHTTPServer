package com.nesterov.dw_hibernate;

import java.io.IOException;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.nesterov.dw_hibernate.auth.PersonAuthenticator;
import com.nesterov.dw_hibernate.auth.PersonAuthorizer;
import com.nesterov.dw_hibernate.db.Names;
import com.nesterov.dw_hibernate.db.PersonDAO;
import com.nesterov.dw_hibernate.model.Person;
import com.nesterov.dw_hibernate.model.User;
import com.nesterov.dw_hibernate.resources.PersonResource;
import com.nimbusds.oauth2.sdk.ParseException;
import com.okta.jwt.JwtHelper;
import com.nesterov.dw_hibernate.auth.*;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

public class AppApplication extends Application<AppConfiguration> {

    public static void main(final String[] args) throws Exception {
        new AppApplication().run(args);
    }

    @Override
    public String getName() {
        return "App";
    }

    @Override
    public void initialize(final Bootstrap<AppConfiguration> bootstrap) {
    	bootstrap.addBundle(hibernate);
    	bootstrap.addBundle(new MultiPartBundle());
    	bootstrap.addBundle(new AssetsBundle("/assets/css", "/css", null, "css"));
    	bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(final AppConfiguration configuration,
                    final Environment environment) throws ParseException, IOException {
       PersonDAO personDAO = new PersonDAO(hibernate.getSessionFactory());
       Names names= new Names();
       final PersonResource personResource = new PersonResource(personDAO, names);
       environment.jersey().register(personResource);
       environment.jersey().register(new AuthDynamicFeature(
               new BasicCredentialAuthFilter.Builder<User>()
                   .setAuthenticator(new PersonAuthenticator("login", "password"))
                   .setAuthorizer(new PersonAuthorizer())
                   .setPrefix("Bearer")
                   .setRealm("SUPER SECRET STUFF")
                   .buildAuthFilter()));
       environment.jersey().register(RolesAllowedDynamicFeature.class);
       environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
     
       
    }
    

    private HibernateBundle<AppConfiguration> hibernate = new HibernateBundle<AppConfiguration>(Person.class) {
    	public PooledDataSourceFactory getDataSourceFactory(AppConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}};
  

}
