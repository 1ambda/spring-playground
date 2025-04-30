package com.github.lambda.opsplatform.config;

import com.github.lambda.opsplatform.config.audit.AccessAuditable;
import com.github.lambda.opsplatform.context.ConverterContext;
import com.github.lambda.opsplatform.context.SecurityContext;
import com.github.lambda.opsplatform.domain.AuditService;
import com.github.lambda.opsplatform.domain.audit.AccessAuditableType;
import com.github.lambda.opsplatform.domain.audit.AuditResourceAction;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
@Aspect
@Slf4j
public class AccessAuditConfig {

  private final AuditService auditService;
  private final SecurityContext securityContext;


  @Pointcut("@annotation(com.github.lambda.opsplatform.config.audit.AccessAuditable)")
  public void onController() {
  }

  @After("onController()")
  public void beforeController(JoinPoint point) {

    Optional<Long> userIdOpt = securityContext.getCurrentUserId();
    if (userIdOpt.isEmpty()) {
      log.debug("User ID not found in security context");
      return;
    }

    AccessAuditable accessAuditable = ((MethodSignature) point.getSignature()).getMethod()
        .getAnnotation(AccessAuditable.class);

    if (accessAuditable == null) {
      log.debug("AccessAuditable annotation not found");
      return;
    }

    HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
        RequestContextHolder.getRequestAttributes())).getRequest();
    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();

    try {
      String controllerName = signature.getDeclaringTypeName();
      String controllerNameActual = controllerName.substring(controllerName.lastIndexOf(".") + 1);
      String controllerFunction = signature.getName();
      String requestUri = URLDecoder.decode(request.getRequestURI(), "UTF-8");
      String requestVerb = request.getMethod();
      String requestQuery = request.getQueryString();

      AccessAuditableType accessAuditableType = accessAuditable.type();
      Map<String, String> auditableParams = getAuditiableParams(point, signature,
          accessAuditableType, request);

      auditableParams.put("context/user_id", String.valueOf(userIdOpt.get()));
      auditableParams.put("controller/name", controllerNameActual);
      auditableParams.put("controller/function", controllerFunction);
      auditableParams.put("request/url", requestUri);
      auditableParams.put("request/query", requestQuery);
      auditableParams.put("request/method", requestVerb);

      String accessContent = ConverterContext.Mapper.writeValueAsString(auditableParams);

      auditService.saveAuditAccess(userIdOpt.get(), accessAuditableType.getCategory(),
          accessContent, AuditResourceAction.READ);

    } catch (Exception e) {
      log.error("Aspect onController: {}", e.getMessage());
    }

    log.info("Controller: {}", point.getSignature().getDeclaringTypeName());
  }

  public Map<String, String> getAuditiableParams(JoinPoint point, MethodSignature signature,
      AccessAuditableType accessAuditableType, HttpServletRequest request) {
    List<String> accessParamQueryNames = List.of(accessAuditableType.getParamQuery());
    List<String> accessParamPayloadNames = List.of(accessAuditableType.getParamPayload());

    Map<String, String> paramPayload = extractParamPayload(point, signature,
        accessParamPayloadNames);
    Map<String, String> paramQuery = extractParamQuery(request, accessParamQueryNames);
    Map<String, String> paramMerged = new HashMap<>();
    paramMerged.putAll(paramQuery);
    paramMerged.putAll(paramPayload);
    return paramMerged;
  }

  private Map<String, String> extractParamQuery(HttpServletRequest request,
      List<String> accessParamQueryNames) {

    if (accessParamQueryNames.isEmpty()) {
      return Map.of();
    }

    Map<String, String> params = new HashMap<>();

    for (String paramName : accessParamQueryNames) {
      String paramValue = request.getParameter(paramName);
      if (paramValue != null) {
        params.put("query/" + paramName, paramValue);
      }
    }

    return params;
  }

  public Map<String, String> extractParamPayload(JoinPoint point, MethodSignature signature,
      List<String> paramNames) {

    if (paramNames.isEmpty()) {
      return Map.of();
    }

    Object[] args = point.getArgs();
    String[] strings = signature.getParameterNames();

    Map<String, String> params = new HashMap<>();
    for (int i = 0; i < strings.length; i++) {
      if (!paramNames.contains(strings[i])) {
        continue;
      }

      params.put("payload/" + strings[i], (String) args[i]);
    }

    return params;
  }

}

