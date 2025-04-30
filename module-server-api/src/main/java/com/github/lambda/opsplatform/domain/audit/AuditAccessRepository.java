package com.github.lambda.opsplatform.domain.audit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditAccessRepository extends JpaRepository<AuditAccessEntity, Long>,
    AuditAccessRepositoryCustom {
  // Custom query methods can be defined here if needed
}
