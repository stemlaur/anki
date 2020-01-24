package com.stemlaur.anki.infrastructure;

import com.stemlaur.anki.domain.study.Session;
import com.stemlaur.anki.domain.study.Sessions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class InMemorySessions implements Sessions {
    private final List<Session> sessions = new ArrayList<>();

    @Override
    public void save(final Session session) {
        this.sessions.add(session);
    }

    @Override
    public Optional<Session> find(final String sessionId) {
        return this.sessions.stream().filter(session -> session.id().equals(sessionId))
                .findFirst();
    }
}
