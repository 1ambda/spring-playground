package com.github.lambda.opsplatform.config;

import com.github.lambda.opsplatform.config.exception.ExceptionCategory;
import com.github.lambda.opsplatform.config.swagger.ApiDocs;
import com.github.lambda.opsplatform.protocol.base.CommonResponse;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI().components(new Components()).info(
        new Info().title("Ops Platform API").version("1.0")
            .description("Ops Platform API Documentation"));
  }

  @Bean
  public OperationCustomizer operationCustomizer() {
    return (operation, handlerMethod) -> {
      this.addResponseBodyWrapper(operation);
      this.addResponseApiDocs(operation, handlerMethod);
      return operation;
    };
  }

  private void addResponseApiDocs(Operation operation, HandlerMethod handlerMethod) {
    ApiDocs apiDocs = handlerMethod.getMethodAnnotation(ApiDocs.class);

    List<ExceptionCategory> exceptionCategories = new ArrayList<>(
        List.of(ExceptionCategory.INTERNAL_ERROR));
    if (apiDocs != null && apiDocs.value() != null && apiDocs.value().length > 0) {
      exceptionCategories.addAll(Arrays.asList(apiDocs.value()));
    }

    ApiResponses responses = operation.getResponses();
    Map<Integer, List<ExceptionCategory>> groupedExceptionCategory = exceptionCategories.stream()
        .collect(Collectors.groupingBy(x -> x.getCode() / 100));

    groupedExceptionCategory.forEach((status, categories) -> {
      Content content = new Content();
      MediaType mediaType = new MediaType();
      ApiResponse response = new ApiResponse();
      response.setDescription(HttpStatus.valueOf(status).getReasonPhrase());

      categories.forEach(exceptionCategory -> {
        Example example = new Example();
        example.setSummary(exceptionCategory.getType());
        example.setDescription(exceptionCategory.getDescription());
        example.setValue(
            CommonResponse.fail(exceptionCategory, exceptionCategory.getDescription()));

        mediaType.addExamples(exceptionCategory.getType(), example);
      });

      content.addMediaType("application/json;charset=UTF-8", mediaType);
      response.setContent(content);
      responses.addApiResponse(String.valueOf(status), response);
    });
  }

  private void addResponseBodyWrapper(Operation operation) {
    final Content content = operation.getResponses().get("200").getContent();
    if (content != null) {
      content.forEach((mediaTypeKey, mediaType) -> {
        Schema<?> originalSchema = mediaType.getSchema();
        Schema<?> wrappedSchema = wrapSchema(originalSchema);
        mediaType.setSchema(wrappedSchema);
      });
    }
  }

  private Schema<?> wrapSchema(Schema<?> originalSchema) {
    final Schema<?> wrapperSchema = new Schema<>();

    wrapperSchema.addProperty("success", new Schema<>().type("boolean").example(true));
    wrapperSchema.addProperty("timestamp",
        new Schema<>().type("string").format("date-time").example(LocalDateTime.now().toString()));
    wrapperSchema.addProperty("data", originalSchema);

    return wrapperSchema;
  }

}
