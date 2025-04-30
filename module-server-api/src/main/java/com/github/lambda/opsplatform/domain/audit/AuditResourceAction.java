package com.github.lambda.opsplatform.domain.audit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuditResourceAction {

  CREATE("CREATE"),
  READ("READ"),
  UPDATE("UPDATE"),
  DELETE("DELETE");

  private final String value;
}
