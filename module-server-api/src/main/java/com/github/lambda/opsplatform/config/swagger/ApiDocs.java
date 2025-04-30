package com.github.lambda.opsplatform.config.swagger;

import com.github.lambda.opsplatform.config.exception.ExceptionCategory;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiDocs {

  ExceptionCategory[] value() default {};
}
