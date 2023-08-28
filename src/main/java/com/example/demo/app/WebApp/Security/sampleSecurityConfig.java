package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class sampleSecurityConfig {
	@Autowired
	private DataSource dataSource;
	
	private AuthenticationManager authentication;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)
	throws Exception{
        
		http

		.authorizeHttpRequests((requests) -> requests
			.requestMatchers("/", "/registration","/self","/static","/admin/addres/x/registrationadmin","/images/**","/userdata").permitAll()
			.requestMatchers("/admin_controller","/admin_controler/adminData").hasAuthority("ROLE_ADMIN")
			.anyRequest().authenticated()
		)
		.formLogin((form) -> form
				.loginPage("/login")
				.permitAll()
		)
		.logout((logout) -> logout.permitAll());

	return http.build();
	}
	@Bean
	public UserDetailsManager userDetailManager() {
		return new JdbcUserDetailsManager(this.dataSource);
		

	}
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
	
