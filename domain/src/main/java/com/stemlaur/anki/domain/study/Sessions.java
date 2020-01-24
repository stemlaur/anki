package com.stemlaur.anki.domain.study;

import java.util.Optional;

public interface Sessions {
    void save(Session session);

    Optional<Session> find(String sessionId);
}
