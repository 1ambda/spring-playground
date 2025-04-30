package com.github.lambda.opsplatform.config.audit;

import com.github.lambda.opsplatform.domain.audit.AccessAuditableType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessAuditable {

  AccessAuditableType type() default AccessAuditableType.ACCESS;

}
