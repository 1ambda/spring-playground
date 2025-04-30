package com.github.lambda.opsplatform.config.audit;

import com.github.lambda.opsplatform.context.SecurityContext;
import com.github.lambda.opsplatform.domain.AuditService;
import com.github.lambda.opsplatform.domain.audit.AuditAccessEntity;
import com.github.lambda.opsplatform.domain.audit.AuditResourceAction;
import com.github.lambda.opsplatform.domain.audit.AuditResourceEntity;
import com.github.lambda.opsplatform.domain.resource.ResourceEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EntityEventListener implements PostInsertEventListener, PostDeleteEventListener,
    PostUpdateEventListener {

  private final AuditService auditService;
  private final SecurityContext securityContext;

  @Override
  public void onPostInsert(PostInsertEvent event) {
    handleEntity(event.getEntity(), AuditResourceAction.CREATE);
  }

  @Override
  public void onPostDelete(PostDeleteEvent event) {
    handleEntity(event.getEntity(), AuditResourceAction.DELETE);
  }

  @Override
  public void onPostUpdate(PostUpdateEvent postUpdateEvent) {
    handleEntity(postUpdateEvent.getEntity(), AuditResourceAction.UPDATE);
  }

  public void handleEntity(Object rawEntity, AuditResourceAction action) {
    if (rawEntity instanceof AuditResourceEntity) {
      // skip audit resource entity
      return;
    }

    if (rawEntity instanceof AuditAccessEntity) {
      // skip audit access entity
      return;
    }

    if (rawEntity instanceof ResourceEntity) {
      ResourceEntity entity = (ResourceEntity) rawEntity;
      auditService.saveAuditResource(securityContext.getCurrentUserIdOrThrow(),
          entity.getClass().getSimpleName(), entity.getId(), action);
      log.info("AuditResourceHistoryEntity created: {}", entity);
    }
  }

  @Override
  public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
    return false;
  }
}
