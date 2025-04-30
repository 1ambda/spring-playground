package com.github.lambda.opsplatform.config.security;

import static com.github.lambda.opsplatform.config.security.CustomAuthPrincipal.CUSTOM_PROPERTY_ID;

import com.github.lambda.opsplatform.domain.UserAggregate;
import com.github.lambda.opsplatform.domain.UserService;
import jakarta.transaction.Transactional;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomAuthService extends OidcUserService {

  private final UserService userService;

  public CustomAuthService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

    try {
      OidcUser oidcUser = super.loadUser(userRequest);
      String email = oidcUser.getEmail();

      // Validate user
      UserAggregate userAggregate = userService.findAggregateByEmailOrThrow(email);
      validateUser(userAggregate);

      // Update user with oidc properties
      userService.syncOidcAdditional(userAggregate, email);

      // Build authorities, properties
      Set<GrantedAuthority> mappedAuthorities = buildAuthorities(userAggregate);
      Map<String, String> customProperties = buildCustomProperties(userAggregate);

      // Create principal
      return CustomAuthPrincipal.of(mappedAuthorities, customProperties, oidcUser);

    } catch (Exception e) {
      throw new InternalAuthenticationServiceException(e.getMessage(), e);
    }

  }

  private Set<GrantedAuthority> buildAuthorities(UserAggregate userAggregate) {
    Set<String> authorities = userAggregate.getAuthorityNames();
    return authorities.stream().map(SimpleGrantedAuthority::new)
        .collect(HashSet::new, HashSet::add, HashSet::addAll);
  }

  private Map<String, String> buildCustomProperties(UserAggregate userAggregate) {
    return Map.of(CUSTOM_PROPERTY_ID, userAggregate.getId().toString());
  }

  private void validateUser(UserAggregate userAggregate) {
    if (!userAggregate.isEnabled()) {
      throw new DisabledException("User is not active");
    }
  }
}
