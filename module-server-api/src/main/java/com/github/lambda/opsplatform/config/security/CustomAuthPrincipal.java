package com.github.lambda.opsplatform.config.security;

import java.io.Serial;
import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Getter
public class CustomAuthPrincipal extends DefaultOidcUser {

  // create constant for custom properties
  public static final String CUSTOM_PROPERTY_ID = "id";
  @Serial
  private static final long serialVersionUID = -2378469202439157250L;
  private Map<String, String> customProperties = Map.of();

  public CustomAuthPrincipal(Collection<? extends GrantedAuthority> authorities,
      OidcIdToken idToken, OidcUserInfo oidcUserInfo, Map<String, String> customProperties) {

    super(authorities, idToken, oidcUserInfo);
    this.customProperties = customProperties;
  }

  public static CustomAuthPrincipal of(Collection<? extends GrantedAuthority> authorities,
      Map<String, String> customProperties, OidcUser oidcUser) {
    return new CustomAuthPrincipal(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo(),
        customProperties);
  }

  public String getCustomProperty(String key) {
    if (customProperties == null || customProperties.isEmpty() || !customProperties.containsKey(
        key)) {
      return null;
    }

    return customProperties.get(key);
  }

  public Long getPropertyId() {
    String id = getCustomProperty(CUSTOM_PROPERTY_ID);
    if (id == null) {
      return null;
    }

    try {
      return Long.valueOf(id);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  public Long getPropertyIdOrThrow() {
    Long id = getPropertyId();
    if (id == null) {
      throw new IllegalArgumentException("User ID cannot be null");
    }
    return id;
  }
}
