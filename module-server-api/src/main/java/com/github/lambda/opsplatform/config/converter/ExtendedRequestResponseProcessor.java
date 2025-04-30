package com.github.lambda.opsplatform.config.converter;

import com.github.lambda.opsplatform.protocol.base.CommonResponse;
import java.io.IOException;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

public class ExtendedRequestResponseProcessor extends RequestResponseBodyMethodProcessor {

  public ExtendedRequestResponseProcessor(List<HttpMessageConverter<?>> converters) {
    super(converters);
  }

  @Override
  public void handleReturnValue(Object returnValue,
      MethodParameter returnType,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest) throws HttpMediaTypeNotAcceptableException, IOException {

    Object processedValue = returnValue instanceof String
        ? CommonResponse.ok(returnValue)
        : returnValue;

    super.handleReturnValue(processedValue, returnType, mavContainer, webRequest);
  }
}