package com.restcountries.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

	@Bean
	public UserDetailsService userDetailsService() {

		UserDetails user1 = User.builder().username("user1").password(passwordEncoder().encode("123")).roles("ADMIN")
				.build();
		UserDetails user2 = User.builder().username("user2").password(passwordEncoder().encode("234")).roles("ADMIN")
				.build();

		return new InMemoryUserDetailsManager(user1, user2);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}