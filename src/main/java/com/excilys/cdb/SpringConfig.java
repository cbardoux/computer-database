package main.java.com.excilys.cdb;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebMvc
@ComponentScan({ "main.java.com.excilys.cdb.dao", "main.java.com.excilys.cdb.service",
		"main.java.com.excilys.cdb.servlets", "main.java.com.excilys.cdb.view",
		"main.java.com.excilys.cdb.validator", "main.java.com.excilys.cdb.controller",
		"main.java.com.excilys.cdb.dto", "main.java.com.excilys.cdb.model"})
public class SpringConfig implements  WebMvcConfigurer{
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();

		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".jsp");

		return bean;
	}

//	@Override
//	protected WebApplicationContext createRootApplicationContext() {
//		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//		context.register(SpringConfig.class);
//		return context;
//	}
	
	@Bean(destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        HikariConfig config = new HikariConfig("/db.properties");
        return new HikariDataSource(config);
    }

}
