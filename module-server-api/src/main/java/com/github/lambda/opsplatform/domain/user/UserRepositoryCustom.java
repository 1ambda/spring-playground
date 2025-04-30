package com.github.lambda.opsplatform.domain.user;

import com.github.lambda.opsplatform.domain.UserAggregate;
import java.util.Optional;

public interface UserRepositoryCustom {

  Optional<UserEntity> findByEmail(String email);

  Optional<UserAggregate> findAggregateByEmail(String email);
}
