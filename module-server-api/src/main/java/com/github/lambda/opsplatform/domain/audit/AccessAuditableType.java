package com.github.lambda.opsplatform.domain.audit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessAuditableType {

  ACCESS("ACCESS", new String[]{}, new String[]{"resourceType"}),
  ;

  private final String category;
  private final String[] paramQuery;
  private final String[] paramPayload;
}
