package com.keepersecurity.automator.config;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.saml2.provider.service.metadata.OpenSaml4MetadataResolver;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.web.DefaultRelyingPartyRegistrationResolver;
import org.springframework.security.saml2.provider.service.web.Saml2MetadataFilter;
import org.springframework.security.saml2.provider.service.web.authentication.Saml2WebSsoAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final RelyingPartyRegistrationRepository relyingPartyRegistrationRepository;

  public SecurityConfig(RelyingPartyRegistrationRepository relyingPartyRegistrationRepository) {
    this.relyingPartyRegistrationRepository = relyingPartyRegistrationRepository;
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    DefaultRelyingPartyRegistrationResolver relyingPartyRegistrationResolver =
        new DefaultRelyingPartyRegistrationResolver(this.relyingPartyRegistrationRepository);
    Saml2MetadataFilter filter =
        new Saml2MetadataFilter(relyingPartyRegistrationResolver, new OpenSaml4MetadataResolver());

    http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
        .saml2Login(withDefaults()).saml2Logout(withDefaults())
        .addFilterBefore(filter, Saml2WebSsoAuthenticationFilter.class);
    return http.build();
  }

  // Register the Protobuf message converter so Spring MVC handles
  // "application/x-protobuf" media type.
  @Bean
  ProtobufHttpMessageConverter protobufHttpMessageConverter() {
    return new ProtobufHttpMessageConverter();
  }
}
