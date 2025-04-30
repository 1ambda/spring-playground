package com.github.lambda.opsplatform.domain.user;

import static com.github.lambda.opsplatform.domain.user.QUserAuthorityEntity.userAuthorityEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserAuthorityRepositoryImpl implements UserAuthorityRepositoryCustom {

  private final JPAQueryFactory factory;

  @Override
  public List<UserAuthorityEntity> findByUserId(Long userId) {
    return factory.
        selectFrom(userAuthorityEntity)
        .where(userAuthorityEntity.userId.eq(userId))
        .fetch();
  }
}
