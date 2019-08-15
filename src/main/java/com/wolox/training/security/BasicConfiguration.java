package com.wolox.training.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class BasicConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false).authenticationProvider(authProvider);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/users/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/books").permitAll()
                .antMatchers(HttpMethod.GET,"/api/users/**").authenticated()
                .antMatchers(HttpMethod.GET,"/api/books/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/books/**").authenticated()
                .antMatchers(HttpMethod.PUT,"/api/users/*").authenticated()
                .antMatchers(HttpMethod.POST,"/api/users/*/book/*").authenticated()
                .antMatchers(HttpMethod.DELETE,"/api/users/**").authenticated()
                .antMatchers(HttpMethod.DELETE,"/api/books/*").authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}

