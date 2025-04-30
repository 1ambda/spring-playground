package com.github.lambda.opsplatform.config;

import com.github.lambda.opsplatform.protocol.base.CommonResponse;
import com.github.lambda.opsplatform.protocol.base.ExceptionResponse;
import java.util.Map;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.github.lambda.opsplatform.controller")
public class ResponseConfig implements ResponseBodyAdvice {

  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType,
      MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request,
      ServerHttpResponse response) {

    if (body instanceof String) {
      return CommonResponse.ok(Map.of("value", body));
    }

    if (!(body instanceof CommonResponse<?>)) {
      return CommonResponse.ok(body);
    }

    return body;
  }
}
