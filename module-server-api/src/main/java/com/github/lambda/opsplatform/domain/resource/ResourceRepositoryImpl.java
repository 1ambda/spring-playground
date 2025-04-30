package com.github.lambda.opsplatform.domain.resource;

import com.github.lambda.opsplatform.context.RepositoryContext;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

  @Override
  public Page<ResourceEntity> search(Pageable pageable) {
    QResourceEntity resource = QResourceEntity.resourceEntity;
    JPAQuery<ResourceEntity> query = factory
        .selectFrom(resource)
        .where(resource.deletedAt.isNotNull())
        .offset(pageable.getOffset())
        .orderBy(RepositoryContext.getSort(pageable, resource))
        .limit(pageable.getPageSize());

    var result = query.fetch();
    var count = factory
        .select(resource.count()).from(resource)
        .where(resource.deletedAt.isNotNull())
        .fetchOne();

    return new PageImpl<>(result, pageable, count == null ? 0 : count);
  }
}
