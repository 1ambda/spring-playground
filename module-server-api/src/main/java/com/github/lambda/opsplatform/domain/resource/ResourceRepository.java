package com.github.lambda.opsplatform.domain.resource;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Long>,
    ResourceRepositoryCustom {

}
