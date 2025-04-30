package com.github.lambda.opsplatform.domain.resource;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
@RequiredArgsConstructor
public class ResourceRepositoryImpl implements ResourceRepositoryCustom {

  private final JPAQueryFactory factory;
  private final EntityManager entityManager;

  @Override
  public void deleteBulkByUserId(Long userId) {
    QResourceEntity resource = QResourceEntity.resourceEntity;
    LocalDateTime now = LocalDateTime.now();
    factory
        .update(resource)
        .set(resource.deletedAt, now)
        .where(resource.userId.eq(userId).and(resource.deletedAt.isNull()))
        .execute();

    entityManager.flush();
    entityManager.clear();
  }
}
