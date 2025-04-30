package com.github.lambda.opsplatform.domain.audit;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuditAccessEntityContent {

  private final String controllerName;
  private final String controllerFunction;
  private final String requestUrl;
  private final String requestQuery;
  private final String requestVerb;

  @Builder
  public AuditAccessEntityContent(String controllerName, String controllerFunction,
      String requestUrl, String requestQuery, String requestVerb) {
    this.controllerName = controllerName;
    this.controllerFunction = controllerFunction;
    this.requestUrl = requestUrl;
    this.requestQuery = requestQuery;
    this.requestVerb = requestVerb;
  }
}
