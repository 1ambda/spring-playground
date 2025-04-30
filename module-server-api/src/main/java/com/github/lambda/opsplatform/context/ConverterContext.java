package com.github.lambda.opsplatform.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class ConverterContext {

  public static final ObjectMapper Mapper = new ObjectMapper();

  static {
    Mapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
  }

}
