package com.github.lambda.opsplatform.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final HttpStatus status;
  private final ExceptionCategory category;

  private ApplicationException(HttpStatus status, ExceptionCategory category) {
    super(category.getType());
    this.status = status;
    this.category = category;
  }

  private ApplicationException(Exception ex, ExceptionCategory category) {
    super(category.getType(), ex);
    this.status = ExceptionCategory.to(category);
    this.category = category;
  }

  public static ApplicationException of(Exception ex, ExceptionCategory code) {
    return new ApplicationException(ex, code);
  }

  public static ApplicationException of(ExceptionCategory code) {
    return new ApplicationException(ExceptionCategory.to(code), code);
  }

}
