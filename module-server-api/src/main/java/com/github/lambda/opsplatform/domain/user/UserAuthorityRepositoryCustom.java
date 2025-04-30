package com.github.lambda.opsplatform.domain.user;

import java.util.List;

public interface UserAuthorityRepositoryCustom {

  public List<UserAuthorityEntity> findByUserId(Long userId);

}
