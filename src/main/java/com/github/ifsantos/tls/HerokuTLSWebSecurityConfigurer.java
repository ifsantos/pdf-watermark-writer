package com.github.ifsantos.tls;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class HerokuTLSWebSecurityConfigurer {

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception{
      http
      // .requiresChannel()
      //   .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
      //     .requiresSecure()
      //   .and()
        .headers()
          .httpStrictTransportSecurity()
            .requestMatcher(AnyRequestMatcher.INSTANCE)
            .includeSubDomains(true)
            .maxAgeInSeconds(31536000)
            .preload(true);
      return http.build();
  }
  @Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("https://localhost:8080");
			}
		};
	}

  @Bean
public ServletWebServerFactory servletContainer() {
  TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
      @Override
      protected void postProcessContext(Context context) {
        SecurityConstraint securityConstraint = new SecurityConstraint();
        securityConstraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        securityConstraint.addCollection(collection);
        context.addConstraint(securityConstraint);
      }
    };
   
  tomcat.addAdditionalTomcatConnectors(redirectConnector());
  return tomcat;
}
 
private Connector redirectConnector() {
  Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
  connector.setScheme("http");
  connector.setPort(8080);
  connector.setSecure(false);
  connector.setRedirectPort(8443);
   
  return connector;
}


}
