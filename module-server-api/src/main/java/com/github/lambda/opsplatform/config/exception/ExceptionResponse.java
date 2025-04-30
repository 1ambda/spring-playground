package com.github.lambda.opsplatform.config.exception;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Schema(title = "Error", accessMode = Schema.AccessMode.READ_ONLY)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExceptionResponse {

  private final int errorCode;
  private final String errorType;
  private final String description;

  public static ExceptionResponse from(ExceptionCategory category) {
    return new ExceptionResponse(category.getCode(), category.getType(), null);
  }

  public static ExceptionResponse from(ExceptionCategory category, String description) {
    return new ExceptionResponse(category.getCode(), category.getType(), description);
  }
}
