package com.mercadolibre.fresco.config;

import com.mercadolibre.fresco.security.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/v1/sign-in").permitAll()
            .antMatchers(HttpMethod.GET, "/ping").permitAll()
            .antMatchers(HttpMethod.GET, "/docs/v1/openapi", "/docs/v1/openapi/**").permitAll()
            .antMatchers(HttpMethod.GET, "/docs/v1", "/docs/swagger-ui.html", "/docs/swagger-ui/**").permitAll()
            .antMatchers(HttpMethod.GET, "/fake").permitAll()
            .anyRequest().authenticated();
    }
}
