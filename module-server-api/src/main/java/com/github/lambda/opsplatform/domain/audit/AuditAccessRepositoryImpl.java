package com.github.lambda.opsplatform.domain.audit;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuditAccessRepositoryImpl implements AuditAccessRepositoryCustom {

  private final JPAQueryFactory factory;
  private final EntityManager entityManager;


}
