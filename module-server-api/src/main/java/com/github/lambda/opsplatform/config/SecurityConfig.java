package com.github.lambda.opsplatform.config;

import com.github.lambda.opsplatform.config.security.CustomAccessDeniedHandler;
import com.github.lambda.opsplatform.config.security.CustomAuthService;
import com.github.lambda.opsplatform.config.security.CustomAuthenticationEntryPoint;
import com.github.lambda.opsplatform.config.security.CustomAuthenticationFailureHandler;
import com.github.lambda.opsplatform.config.security.CustomAuthenticationSuccessHandler;
import com.github.lambda.opsplatform.config.security.CustomExceptionFilter;
import com.github.lambda.opsplatform.config.security.CustomLogoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomAuthenticationEntryPoint authenticationEntryPoint;
  private final CustomAccessDeniedHandler accessDeniedHandler;
  private final CustomAuthService customAuthService;
  private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
  private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
  private final CustomLogoutHandler customLogoutHandler;
  private final CustomExceptionFilter customExceptionFilter;
  @Value("${app.logging.security}")
  private boolean appLoggingSecurity;

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.debug(appLoggingSecurity).ignoring()
        .requestMatchers("/api-docs/**", "/swagger-ui/**", "/swagger-resources/**")
        .requestMatchers("/favicon.ico", "/error", "/actuator/**")
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // https://github.com/hardikSinghBehl/jwt-auth-flow-spring-security/blob/master/src/main/java/com/behl/cerberus/utility/ApiEndpointSecurityInspector.java#L41

    // @formatter:off
    http.csrf(AbstractHttpConfigurer::disable);

    http.authorizeHttpRequests(
        (authorize) -> authorize.requestMatchers(
            "/", "/api/session/**", "/unauthenticated", "/oauth2/**", "/login/**")
            .permitAll()
            .anyRequest()
            .authenticated()
    );

    http.logout(logout -> {
      logout.logoutUrl("/api/session/logout")
          .invalidateHttpSession(true)
          .deleteCookies("JSESSIONID")
          .clearAuthentication(true)
          .logoutSuccessHandler(customLogoutHandler);
    });

    // http://localhost:8070/oauth2/authorization/keycloak
    http.exceptionHandling(e -> {
      e.authenticationEntryPoint(authenticationEntryPoint);
      e.accessDeniedHandler(accessDeniedHandler);
    });

    http.oauth2Login(customizer -> {
      customizer
          .userInfoEndpoint(x -> x.oidcUserService(customAuthService))
          .successHandler(customAuthenticationSuccessHandler)
          .failureHandler(customAuthenticationFailureHandler);
      }
    );

    http.sessionManagement(session -> {
      session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
      session.sessionAuthenticationFailureHandler(customAuthenticationFailureHandler);
    });

    http.addFilterBefore(customExceptionFilter, OAuth2LoginAuthenticationFilter.class);

    // @formatter:on

    return http.build();
  }
}
