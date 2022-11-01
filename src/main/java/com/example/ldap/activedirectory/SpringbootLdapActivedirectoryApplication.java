package com.example.ldap.activedirectory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringbootLdapActivedirectoryApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources(SpringbootLdapActivedirectoryApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootLdapActivedirectoryApplication.class, args);
	}

}
