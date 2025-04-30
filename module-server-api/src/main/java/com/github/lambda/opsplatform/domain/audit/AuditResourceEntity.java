package com.github.lambda.opsplatform.domain.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@NoArgsConstructor
@Entity(name = "audit_resource_history")
@Table(name = "audit_resource_history", schema = "public")
public class AuditResourceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @Column(name = "user_id", nullable = false, updatable = false)
  private Long userId;

  @Column(name = "resource_type", nullable = false)
  private String resourceType;

  @Column(name = "resource_id", nullable = false)
  private Long resourceId;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "resource_content", columnDefinition = "json")
  private String resourceContent;

  @Column(name = "action", nullable = false, updatable = false)
  private String action;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public AuditResourceEntity(Long userId, String resourceType, Long resourceId,
      String resourceContent, AuditResourceAction action) {
    this.userId = userId;
    this.resourceType = resourceType;
    this.resourceId = resourceId;
    this.resourceContent = resourceContent;
    this.action = action.getValue();
  }
}
