package com.excilys.cdb.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({ "com.excilys.cdb.service" })
@Import(com.excilys.cdb.persistence.config.PersistenceSpringConfig.class)
public class ServiceSpringConfig {
}
