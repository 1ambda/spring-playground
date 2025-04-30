package com.github.lambda.opsplatform.config;

import com.github.lambda.opsplatform.config.exception.ApplicationException;
import com.github.lambda.opsplatform.config.exception.ExceptionCategory;
import com.github.lambda.opsplatform.protocol.base.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class ExceptionConfig {

  @ExceptionHandler({ApplicationException.class})
  public ResponseEntity<CommonResponse<Void>> handleApplicationException(ApplicationException e) {
    log.error("ApplicationException: {}", e.getMessage(), e);

    return buildResponse(e.getCategory(), e.getMessage());
  }

  @ExceptionHandler({Exception.class, SerializationException.class})
  public ResponseEntity<CommonResponse<Void>> handleInternalServerError(RuntimeException e) {
    log.error("UnknownException: {}", e.getMessage(), e);

    return buildResponse(ExceptionCategory.INTERNAL_ERROR, e.getMessage());
  }

  @ExceptionHandler({UsernameNotFoundException.class,})
  public ResponseEntity<CommonResponse<Void>> handleBadRequest(RuntimeException e) {
    return buildResponse(ExceptionCategory.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler({AuthenticationException.class})
  public ResponseEntity<CommonResponse<Void>> handleAuthenticationException(RuntimeException e) {
    return buildResponse(ExceptionCategory.UNAUTHORIZED, e.getMessage());
  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<CommonResponse<Void>> handleAccessDeniedException(RuntimeException e) {
    return buildResponse(ExceptionCategory.FORBIDDEN, e.getMessage());
  }

  @ExceptionHandler({NoResourceFoundException.class})
  public ResponseEntity<CommonResponse<Void>> handleNoresourceFoundException(RuntimeException e) {
    return buildResponse(ExceptionCategory.NOT_FOUND, e.getMessage());
  }

  private ResponseEntity<CommonResponse<Void>> buildResponse(ExceptionCategory category,
      String message) {
    HttpStatus status = ExceptionCategory.to(category);
    CommonResponse<Void> response = CommonResponse.fail(category, message);
    return ResponseEntity.status(status).body(response);
  }
}
