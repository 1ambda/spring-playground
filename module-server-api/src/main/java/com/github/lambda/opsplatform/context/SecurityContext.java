package com.github.lambda.opsplatform.context;

import com.github.lambda.opsplatform.config.security.CustomAuthPrincipal;
import com.github.lambda.opsplatform.protocol.SessionResponse;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class SecurityContext {

  public static Optional<Long> getCurrentUserId() {
    org.springframework.security.core.context.SecurityContext context = SecurityContextHolder.getContext();
    if (context == null || context.getAuthentication() == null) {
      return Optional.empty();
    }

    Object principal = context.getAuthentication().getPrincipal();
    if (principal instanceof CustomAuthPrincipal) {
      CustomAuthPrincipal authPrincipal = (CustomAuthPrincipal) principal;
      return Optional.of(authPrincipal.getPropertyId());
    }

    return Optional.empty();
  }

  public static Long getCurrentUserIdOrThrow() {
    return getCurrentUserId().orElseThrow(
        () -> new IllegalArgumentException("User ID not found in security context"));
  }

  public static SessionResponse of(Principal principal) {

    if (principal instanceof OAuth2AuthenticationToken) {
      CustomAuthPrincipal customPrincipal = (CustomAuthPrincipal) ((OAuth2AuthenticationToken) principal).getPrincipal();
      String email = customPrincipal.getEmail();
      String userId = customPrincipal.getCustomProperty(CustomAuthPrincipal.CUSTOM_PROPERTY_ID);
      List<String> roles = customPrincipal.getAuthorities().stream()
          .map(GrantedAuthority::getAuthority).toList();

      return SessionResponse.builder().userId(userId).email(email).roles(roles).authenticated(true)
          .build();
    }

    return SessionResponse.builder().userId(null).email(null).roles(List.of()).authenticated(false)
        .build();
  }

}
