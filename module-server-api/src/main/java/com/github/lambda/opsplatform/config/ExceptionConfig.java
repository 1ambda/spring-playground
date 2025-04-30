package com.github.lambda.opsplatform.config;

import com.github.lambda.opsplatform.config.exception.ApplicationException;
import com.github.lambda.opsplatform.config.exception.ExceptionCategory;
import com.github.lambda.opsplatform.config.exception.ExceptionResponse;
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
  public ResponseEntity<ExceptionResponse> handleApplicationException(ApplicationException e) {

    return buildResponse(e.getCategory(), e.getMessage());
  }

  @ExceptionHandler({Exception.class, SerializationException.class})
  public ResponseEntity<ExceptionResponse> handleInternalServerError(RuntimeException e) {
    log.error("UnknownException: {}", e.getMessage(), e);

    return buildResponse(ExceptionCategory.INTERNAL_ERROR, e.getMessage());
  }

  @ExceptionHandler({UsernameNotFoundException.class,})
  public ResponseEntity<ExceptionResponse> handleBadRequest(RuntimeException e) {
    return buildResponse(ExceptionCategory.NOT_FOUND_USER, e.getMessage());
  }

  @ExceptionHandler({AuthenticationException.class})
  public ResponseEntity<ExceptionResponse> handleAuthenticationException(RuntimeException e) {
    return buildResponse(ExceptionCategory.UNAUTHORIZED, e.getMessage());
  }

  @ExceptionHandler({AccessDeniedException.class})
  public ResponseEntity<ExceptionResponse> handleAccessDeniedException(RuntimeException e) {
    return buildResponse(ExceptionCategory.FORBIDDEN, e.getMessage());
  }

  @ExceptionHandler({NoResourceFoundException.class})
  public ResponseEntity<ExceptionResponse> handleNoresourceFoundException(RuntimeException e) {
    return buildResponse(ExceptionCategory.NOT_FOUND, e.getMessage());
  }

  private ResponseEntity<ExceptionResponse> buildResponse(ExceptionCategory category,
      String message) {
    HttpStatus status = ExceptionCategory.to(category);
    ExceptionResponse response = ExceptionResponse.from(category, message);
    return ResponseEntity.status(status).body(response);
  }
}
