package com.excilys.cdb.console.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.excilys.cdb.dao", "com.excilys.cdb.view",
		"com.excilys.cdb.model", "com.excilys.cdb.controller",
		"com.excilys.cdb.dto"})
public class ConsoleSpringConfig implements WebMvcConfigurer {
}
