package jp.yossy.loto6.controller;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomContainer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		factory.addErrorPages(
				new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"),
				new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
	}

}
