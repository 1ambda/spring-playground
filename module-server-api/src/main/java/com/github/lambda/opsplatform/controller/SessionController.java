package com.github.lambda.opsplatform.controller;

import com.github.lambda.opsplatform.context.SecurityContext;
import com.github.lambda.opsplatform.protocol.SessionProtocol;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

  @Value("${app.login-uri}")
  private String loginUri;

  @GetMapping("/api/session/whoami")
  public SessionProtocol whoami(Principal principal) {
    SessionProtocol sessionProtocol = SecurityContext.of(principal);
    return sessionProtocol;
  }

  @GetMapping("/api/session/login")
  public void login(HttpServletResponse response) throws IOException {
    response.sendRedirect(loginUri);
  }
}
