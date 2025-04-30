package com.github.lambda.opsplatform.protocol.base;

import com.github.lambda.opsplatform.config.exception.ExceptionCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Builder(access = lombok.AccessLevel.PRIVATE)
@Schema(title = "ExceptionResponse", accessMode = Schema.AccessMode.READ_ONLY)
public class ExceptionResponse {

  private final int code;
  private final String type;
  private final String description;

  protected static ExceptionResponse of(ExceptionCategory category) {
    return ExceptionResponse.builder()
        .code(category.getCode())
        .type(category.getType())
        .description(null)
        .build();
  }

  protected static ExceptionResponse of(ExceptionCategory category, String description) {
    return ExceptionResponse.builder()
        .code(category.getCode())
        .type(category.getType())
        .description(description)
        .build();
  }
}
