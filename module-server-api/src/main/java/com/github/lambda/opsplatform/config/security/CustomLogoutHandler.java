package com.github.lambda.opsplatform.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler extends SimpleUrlLogoutSuccessHandler {

  @Value("${app.logout-success-uri}")
  private String appLogoutSuccessUri;

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    this.setDefaultTargetUrl(appLogoutSuccessUri);
    super.onLogoutSuccess(request, response, authentication);
  }
}
