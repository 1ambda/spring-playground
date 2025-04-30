package com.github.lambda.opsplatform.domain.user;

import static com.github.lambda.opsplatform.domain.user.QUserAuthorityEntity.userAuthorityEntity;
import static com.github.lambda.opsplatform.domain.user.QUserEntity.userEntity;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.github.lambda.opsplatform.domain.UserAggregate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

  private final JPAQueryFactory factory;
  private final EntityManager entityManager;

  @Override
  public Optional<UserEntity> findByEmail(String email) {
    return Optional.ofNullable(
        factory.selectFrom(userEntity).where(userEntity.email.eq(email)).fetchOne());
  }

  @Override
  public Optional<UserAggregate> findAggregateByEmail(String email) {
    List<UserAggregate> userAggregate = factory
        .from(userEntity)
        .join(userAuthorityEntity)
        .on(userAuthorityEntity.userId.eq(userEntity.id))
        .where(userEntity.email.eq(email))
        .transform(
            groupBy(userEntity.id)
                .list(
                    Projections.constructor(
                        UserAggregate.class,
                        userEntity,
                        list(userAuthorityEntity)
                    )
                )
        );

    return userAggregate.stream().findFirst();
  }
}
