package com.github.lambda.opsplatform.controller;

import com.github.lambda.opsplatform.config.audit.AccessAuditable;
import com.github.lambda.opsplatform.config.exception.ExceptionCategory;
import com.github.lambda.opsplatform.config.swagger.ApiDocs;
import com.github.lambda.opsplatform.context.SecurityContext;
import com.github.lambda.opsplatform.domain.ResourceService;
import com.github.lambda.opsplatform.domain.resource.ResourceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResourceController {

  private final ResourceService resourceService;

  @ApiDocs(value = {ExceptionCategory.BAD_REQUEST, ExceptionCategory.INVALID_PARAMETER,
      ExceptionCategory.SERVER_UNKNOWN,})
  @AccessAuditable
  @GetMapping("/api/resource/lock")
  public String lock(@RequestParam(defaultValue = "resource") String resourceType) {
    Long userId = SecurityContext.getCurrentUserIdOrThrow();
    resourceService.saveResource(userId, resourceType);

    return resourceType;
  }

  @AccessAuditable
  @GetMapping("/api/resource/release")
  public String release() {
    Long userId = SecurityContext.getCurrentUserIdOrThrow();
    resourceService.deleteResource(userId);

    return "resource";
  }

  @AccessAuditable
  @GetMapping("/api/resource")
  public Page<ResourceEntity> resources(
      @PageableDefault(page = 0, size = 10, sort = {"createdAt", "desc"}) Pageable pageable) {
    var searched = resourceService.getResources(pageable);

    return searched;
  }

}
