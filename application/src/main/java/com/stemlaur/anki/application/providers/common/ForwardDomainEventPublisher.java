package com.stemlaur.anki.application.providers.common;

import com.stemlaur.anki.domain.common.DomainEvent;
import com.stemlaur.anki.domain.common.spi.DomainEvents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

@Slf4j
@RequiredArgsConstructor
public class ForwardDomainEventPublisher implements DomainEvents {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(final DomainEvent event) {
        log.debug("Forwarding event {}", event);
        applicationEventPublisher.publishEvent(event);
    }
}
