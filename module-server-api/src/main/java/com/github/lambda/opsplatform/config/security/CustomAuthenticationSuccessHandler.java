package com.github.lambda.opsplatform.config.security;

import com.github.lambda.opsplatform.domain.user.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Value("${app.login-success-uri}")
  private String appLoginSuccessUri;

  private UserRepository userRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    this.setDefaultTargetUrl(appLoginSuccessUri);
    this.setAlwaysUseDefaultTargetUrl(true);

    super.onAuthenticationSuccess(request, response, authentication);
  }
}
