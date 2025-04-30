package com.github.lambda.opsplatform.domain.audit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditResourceRepository extends JpaRepository<AuditResourceEntity, Long>,
    AuditResourceRepositoryCustom {
  // Custom query methods can be defined here if needed
}
