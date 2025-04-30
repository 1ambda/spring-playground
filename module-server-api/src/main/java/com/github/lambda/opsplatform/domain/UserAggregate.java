package com.github.lambda.opsplatform.domain;

import com.github.lambda.opsplatform.domain.user.UserAuthorityEntity;
import com.github.lambda.opsplatform.domain.user.UserEntity;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class UserAggregate {

  private final UserEntity user;
  private final List<UserAuthorityEntity> authorities;

  public Set<String> getAuthorityNames() {
    Set<String> stringified = new HashSet<>();

    stringified.add(user.getRole().name());
    for (UserAuthorityEntity authority : authorities) {
      stringified.add(authority.getAuthority());
    }

    return stringified;
  }

  public boolean isEnabled() {
    return user.isEnabled();
  }

  public Long getId() {
    return user.getId();
  }
}
