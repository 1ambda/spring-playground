package com.github.lambda.opsplatform.config;

import com.github.lambda.opsplatform.config.audit.EntityEventListener;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityEventConfig {

  private final EntityEventListener entityEventListener;

  private final EntityManagerFactory entityManagerFactory;

  @PostConstruct
  public void init() {
    SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
    EventListenerRegistry registry = sessionFactory.getServiceRegistry()
        .getService(EventListenerRegistry.class);

    registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(entityEventListener);
    registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(entityEventListener);
    registry.getEventListenerGroup(EventType.POST_DELETE).appendListener(entityEventListener);
  }


}
