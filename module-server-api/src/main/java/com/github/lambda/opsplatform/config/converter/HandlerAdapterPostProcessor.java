package com.github.lambda.opsplatform.config.converter;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

@Component
public class HandlerAdapterPostProcessor implements BeanPostProcessor {

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) {
    if (bean instanceof RequestMappingHandlerAdapter) {
      RequestMappingHandlerAdapter handlerAdapter = (RequestMappingHandlerAdapter) bean;
      List<HandlerMethodReturnValueHandler> handlers = handlerAdapter.getReturnValueHandlers();
      if (handlers == null) {
        return bean;
      }

      // Find the index of the RequestResponseBodyMethodProcessor
      int index = -1;
      for (int i = 0; i < handlers.size(); i++) {
        if (handlers.get(i) instanceof RequestResponseBodyMethodProcessor) {
          index = i;
          break;
        }
      }

      if (index != -1) {
        List<HandlerMethodReturnValueHandler> newHandlers = new ArrayList<>();
        newHandlers.addAll(handlers.subList(0, index));
        newHandlers.add(new ExtendedRequestResponseProcessor(handlerAdapter.getMessageConverters()));
        newHandlers.addAll(handlers.subList(index, handlers.size()));
        handlerAdapter.setReturnValueHandlers(newHandlers);
      }
    }
    return bean;
  }
}