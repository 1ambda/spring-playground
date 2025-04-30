package com.github.lambda.opsplatform.protocol;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class SessionProtocol {
  private final boolean authenticated;
  private final String userId;
  private final String email;
  private final List<String> roles;
}
