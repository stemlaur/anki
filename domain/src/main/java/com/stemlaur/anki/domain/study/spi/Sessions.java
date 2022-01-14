package com.stemlaur.anki.domain.study.spi;

import com.stemlaur.anki.domain.study.Session;

import java.util.Optional;

public interface Sessions {
    void save(Session session);

    Optional<Session> find(String sessionId);
}
