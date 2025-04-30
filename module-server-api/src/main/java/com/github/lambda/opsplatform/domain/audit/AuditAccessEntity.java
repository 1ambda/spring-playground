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
@Entity
@Table(name = "audit_access_history", schema = "public")
public class AuditAccessEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;

  @Column(name = "user_id", nullable = false, updatable = false)
  private Long userId;

  @Column(name = "access_type", nullable = false, updatable = false)
  private String accessType;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "access_content", updatable = false, columnDefinition = "json")
  private String accessContent;

  @Column(name = "action", nullable = false, updatable = false)
  private String action;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Builder
  public AuditAccessEntity(Long userId, String accessType, String accessContent,
      AuditResourceAction action) {
    this.userId = userId;
    this.accessType = accessType;
    this.accessContent = accessContent;
    this.action = action.getValue();
  }
}
