package com.github.lambda.opsplatform.config;

import com.github.lambda.opsplatform.config.audit.EntityAuditProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class EntityAuditConfig {

  @Bean
  public AuditorAware<Long> auditorProvider() {
    return new EntityAuditProvider();
  }
}
