package com.github.lambda.opsplatform.config;

import com.github.lambda.opsplatform.config.sql.SqlLogEventListener;
import com.github.lambda.opsplatform.config.sql.SqlLogFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SqlLogConfig {

  @Bean
  public SqlLogEventListener p6SpyCustomEventListener() {
    return new SqlLogEventListener();
  }

  @Bean
  public SqlLogFormatter p6SpyCustomFormatter() {
    return new SqlLogFormatter();
  }
}
