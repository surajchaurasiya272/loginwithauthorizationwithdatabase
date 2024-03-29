package com.example.olginregister.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.example.olginregister.service.CustomService;

@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomService customService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection for simplicity, but it's recommended to keep it enabled
                .csrf().disable()
                .authorizeRequests()
                // Allow access to the signin page without authentication
                .antMatchers("/signin").permitAll()
                // Allow access to resources under /public for users with role "NORMAL"
                .antMatchers("/public/**").hasRole("NORMAL")
                // Allow access to /users/ endpoint for users with role "ADMIN"
                .antMatchers("/users/**").hasRole("ADMIN")
                // For any other request, the user must be authenticated
                .anyRequest().authenticated()
                .and()
                // Use form-based authentication
                .formLogin()
                .loginPage("/signin") // The custom login page
                .loginProcessingUrl("/dologin") // The URL to submit the login form to
                .defaultSuccessUrl("/users/") // On successful login, redirect to this URL
                .permitAll(); // Allow all users to access the login page
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Set the custom user details service and password encoder
    auth.userDetailsService(customService).passwordEncoder(passwordEncoder());

        // auth.defaultUserDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // Use BCryptPasswordEncoder with strength of 10 (recommended value)
        return new BCryptPasswordEncoder(10);
    }
}
