package com.hexaware.book.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
	        security
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/api/books", "/api/books/**").permitAll()  
	                .requestMatchers("/api/books").hasRole("ADMIN")             
	                .requestMatchers("/api/books/**").hasRole("ADMIN")          
	                .anyRequest().authenticated()
	            )
	            .httpBasic(Customizer.withDefaults())
	            .csrf(csrf -> csrf.disable()); 

	        return security.build();
	    }
	 @Bean
	    public UserDetailsService userDetailsService() {

	        UserDetails admin = User.withDefaultPasswordEncoder()
	            .username("Sai")
	            .password("Sai@123")
	            .roles("ADMIN")
	            .build();

	        return new InMemoryUserDetailsManager(admin);
	    }

}

