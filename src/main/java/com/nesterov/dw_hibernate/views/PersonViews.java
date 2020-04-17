package com.nesterov.dw_hibernate.views;

import io.dropwizard.views.View;

public class PersonViews extends View{

	public PersonViews() {
		super("/views/file.mustache");
	}
}
