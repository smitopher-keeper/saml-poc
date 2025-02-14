package com.keepersecurity.automator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(authorize -> authorize
          .anyRequest().authenticated()
      )
      // Enable SAML2 Login for SAML-based authentication.
      .saml2Login(Customizer.withDefaults());
    return http.build();
  }

  // Register the Protobuf message converter so Spring MVC handles
  // "application/x-protobuf" media type.
  // @Bean
  // ProtobufHttpMessageConverter protobufHttpMessageConverter() {
  // return new ProtobufHttpMessageConverter();
  // }
}
