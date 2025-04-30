package com.github.lambda.opsplatform.protocol.base;

import com.github.lambda.opsplatform.config.exception.ExceptionCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Builder(access = lombok.AccessLevel.PRIVATE)
@Schema(title = "CommonResponse", accessMode = Schema.AccessMode.READ_ONLY)
public class CommonResponse<D> {

  private final D data;
  private final boolean success;
  private final LocalDateTime timestamp = LocalDateTime.now();
  private final ExceptionResponse error;

  public static <T> CommonResponse<T> ok(T data) {
    return CommonResponse.<T>builder()
        .data(data)
        .success(true)
        .build();
  }

  public static <T> CommonResponse<T> fail(ExceptionCategory category) {
    return CommonResponse.<T>builder()
        .data(null)
        .success(false)
        .error(ExceptionResponse.of(category))
        .build();
  }

  public static <T> CommonResponse<T> fail(ExceptionCategory category, String description) {
    return CommonResponse.<T>builder()
        .data(null)
        .success(false)
        .error(ExceptionResponse.of(category, description))
        .build();
  }
}
