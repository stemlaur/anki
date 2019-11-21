package com.stemlaur.anki.application.infrastructure;

import com.stemlaur.anki.domain.study.Session;
import com.stemlaur.anki.domain.study.SessionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class InMemorySessionRepository implements SessionRepository {
    private final List<Session> sessions = new ArrayList<>();

    @Override
    public void save(final Session session) {
        this.sessions.add(session);
    }

    @Override
    public Optional<Session> findById(final String sessionId) {
        return this.sessions.stream().filter(session -> session.id().equals(sessionId))
                .findFirst();
    }
}
