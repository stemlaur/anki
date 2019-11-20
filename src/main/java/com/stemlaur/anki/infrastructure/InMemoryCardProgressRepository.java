package com.stemlaur.anki.infrastructure;

import com.stemlaur.anki.domain.CardProgress;
import com.stemlaur.anki.domain.CardProgressRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class InMemoryCardProgressRepository implements CardProgressRepository {
    private final List<CardProgress> cardProgresses = new ArrayList<>();

    @Override
    public Optional<CardProgress> findCardProgressById(final String id) {
        return this.cardProgresses.stream().filter(cardProgress -> cardProgress.id().equals(id))
                .findFirst();
    }

    @Override
    public void save(final CardProgress cardProgress) {
        this.cardProgresses.add(cardProgress);
    }
}
