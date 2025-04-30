package com.github.lambda.opsplatform.domain.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResourceRepositoryCustom {

  public void deleteBulkByUserId(Long userId);
  public Page<ResourceEntity> search(Pageable pageable);
}
