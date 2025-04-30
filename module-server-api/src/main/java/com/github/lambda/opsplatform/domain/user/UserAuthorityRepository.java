package com.github.lambda.opsplatform.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthorityEntity, Long>,
    UserAuthorityRepositoryCustom {

}
