package com.github.ifsantos.tls;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class HerokuTLSWebSecurityConfigurer {

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception{
      http.requiresChannel()
        .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
        .requiresSecure();
      return http.build();
  }

}
