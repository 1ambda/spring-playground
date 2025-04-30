package com.github.lambda.opsplatform.domain;

import com.github.lambda.opsplatform.domain.audit.AuditAccessEntity;
import com.github.lambda.opsplatform.domain.audit.AuditAccessRepository;
import com.github.lambda.opsplatform.domain.audit.AuditResourceAction;
import com.github.lambda.opsplatform.domain.audit.AuditResourceEntity;
import com.github.lambda.opsplatform.domain.audit.AuditResourceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuditService {

  private final AuditAccessRepository auditAccessRepository;
  private final AuditResourceRepository auditResourceRepository;

  public void saveAuditResource(Long userId, String resourceType, Long resourceId,
      AuditResourceAction action) {
    AuditResourceEntity auditResourceEntity = AuditResourceEntity.builder().userId(userId)
        .resourceType(resourceType).resourceId(resourceId).action(action).build();

    auditResourceRepository.save(auditResourceEntity);
  }

  public void saveAuditAccess(Long userId, String accessType, String accessContent,
      AuditResourceAction action) {

    AuditAccessEntity auditAccessEntity = AuditAccessEntity.builder().userId(userId)
        .accessType(accessType).accessContent(accessContent).action(action).build();

    auditAccessRepository.save(auditAccessEntity);
  }

}
