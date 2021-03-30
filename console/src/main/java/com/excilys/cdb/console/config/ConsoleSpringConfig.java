package com.excilys.cdb.console.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({ "com.excilys.cdb.view", "com.excilys.cdb.controller.cli", "com.excilys.cdb.app"})
@Import(com.excilys.cdb.service.config.ServiceSpringConfig.class)
public class ConsoleSpringConfig {
}
