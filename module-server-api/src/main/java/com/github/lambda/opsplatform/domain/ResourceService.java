package com.github.lambda.opsplatform.domain;

import com.github.lambda.opsplatform.domain.resource.ResourceEntity;
import com.github.lambda.opsplatform.domain.resource.ResourceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ResourceService {

  private final ResourceRepository resourceRepository;

  public void saveResource(Long userId, String resource) {
    ResourceEntity resourceEntity = ResourceEntity.builder()
        .userId(userId)
        .resource(resource)
        .build();

    resourceRepository.save(resourceEntity);
  }

  public void deleteResource(Long userId) {
    resourceRepository.deleteBulkByUserId(userId);
  }

  public Page<ResourceEntity> getResources(Pageable pageable) {
    return resourceRepository.search(pageable);
  }
}
