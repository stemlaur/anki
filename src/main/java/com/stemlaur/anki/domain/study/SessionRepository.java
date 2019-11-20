package com.stemlaur.anki.domain.study;

import java.util.Optional;

public interface SessionRepository {
    void save(Session session);

    Optional<Session> findById(String sessionId);
}
