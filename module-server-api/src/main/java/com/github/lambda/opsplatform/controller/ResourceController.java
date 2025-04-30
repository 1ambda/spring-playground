package com.github.lambda.opsplatform.controller;

import com.github.lambda.opsplatform.config.audit.AccessAuditable;
import com.github.lambda.opsplatform.context.SecurityContext;
import com.github.lambda.opsplatform.domain.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.lang.annotation.Target;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResourceController {

  private final ResourceService resourceService;

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

}
