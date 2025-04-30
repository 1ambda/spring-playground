package com.github.lambda.opsplatform.domain.user;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

  ADMIN("ADMIN", List.of("ROLE.admin")),
  CONSUMER("CONSUMER", List.of("ROLE.consumer")),
  PUBLIC("PUBLIC", List.of("ROLE.public"));

  private final String value;
  private final List<String> scopes;
}
