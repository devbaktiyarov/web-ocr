package com.devbaktiyarov.webocr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.devbaktiyarov.webocr.config.security.PasswordEncoderFactories;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    
    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> csrf.disable())
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/").permitAll()
				.anyRequest().authenticated()
			)
			.formLogin((form) -> form
                .loginPage("/login")
				.permitAll()
			)
			.logout((logout) -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.clearAuthentication(true)
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll());

		return http.build();
	}

    @Bean
	public UserDetailsService userDetailsService() {

        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
		UserDetails user =
			 User.withUsername("user")
				.password(passwordEncoder().encode("password"))
				.roles("USER")
				.build();
        userDetailsService.createUser(user);
		return userDetailsService;
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/fonts/**", "/images/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    

}
