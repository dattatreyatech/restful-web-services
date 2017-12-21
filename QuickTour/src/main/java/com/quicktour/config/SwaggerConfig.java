package com.quicktour.config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import io.swagger.jaxrs.config.BeanConfig;

public class SwaggerConfig extends HttpServlet {

	private static final long serialVersionUID = -355638547611244099L;
	@Override
	public void init(ServletConfig config) throws ServletException {
		BeanConfig bConfig=new BeanConfig();
		bConfig.setBasePath("/QuickTour/api");
		bConfig.setHost("localhost:8080");
		bConfig.setTitle("QuickTour Services");
		bConfig.setResourcePackage("com.quicktour.resources");
		bConfig.setScan(true);
		
	}
}
