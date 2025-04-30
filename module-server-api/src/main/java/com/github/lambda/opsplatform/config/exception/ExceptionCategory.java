package com.github.lambda.opsplatform.config.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExceptionCategory {

  BAD_REQUEST(40000, "BAD_REQUEST"),
  INVALID_PARAMETER(40001, "INVALID_PARAMETER"),

  UNAUTHORIZED(40100, "UNAUTHORIZED"),
  FORBIDDEN(40300, "FORBIDDEN"),

  NOT_FOUND(40400, "NOT_FOUND"),
  NOT_FOUND_USER(40401, "NOT_FOUND_USER"),

  SERVER_UNKNOWN(50000, "SERVER_UNKNOWN"),
  INTERNAL_ERROR(50000, "INTERNAL_ERROR");

  private final int code;
  private final String type;
  private final String description = "";

  public static HttpStatus to(ExceptionCategory code) {
    if (code.getCode() >= 40000 && code.getCode() < 40100) {
      return HttpStatus.BAD_REQUEST;
    }

    if (code.getCode() >= 40100 && code.getCode() < 40300) {
      return HttpStatus.UNAUTHORIZED;
    }

    if (code.getCode() >= 40300 && code.getCode() < 40400) {
      return HttpStatus.FORBIDDEN;
    }

    if (code.getCode() >= 40400 && code.getCode() < 50000) {
      return HttpStatus.NOT_FOUND;
    }

    return HttpStatus.INTERNAL_SERVER_ERROR;
  }

}
