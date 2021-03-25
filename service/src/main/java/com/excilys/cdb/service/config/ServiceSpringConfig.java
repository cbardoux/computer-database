package com.excilys.cdb.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.excilys.cdb.dao", "com.excilys.cdb.service" })
public class ServiceSpringConfig implements WebMvcConfigurer {
}
