package com.github.lambda.opsplatform.config.audit;

import com.github.lambda.opsplatform.context.SecurityContext;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class EntityAuditProvider implements AuditorAware<Long> {

  @Override
  public Optional<Long> getCurrentAuditor() {
    return SecurityContext.getCurrentUserId();
  }
}
