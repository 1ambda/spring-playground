package com.github.lambda.opsplatform.protocol;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class SessionResponse {
  private final boolean authenticated;
  private final String userId;
  private final String email;
  private final List<String> roles;
}
