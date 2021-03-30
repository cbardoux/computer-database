package com.excilys.cdb.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.service.UserDetailsServiceImpl;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired	
	private UserDAO user;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(user);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().mvcMatchers("/home", "/home/add", "/home/edit").hasAnyRole("ADMIN", "USER")
          .and()
                .authorizeRequests().mvcMatchers("/login", "/resources/**", "/api").permitAll()
          .and()
          		.formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").permitAll()
                .successForwardUrl("/home")
                .failureUrl("/login?error=true")
          .and()
                .logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
          .and()
                .csrf().disable();
    }
}
