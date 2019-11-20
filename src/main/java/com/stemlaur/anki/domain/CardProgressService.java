package com.stemlaur.anki.domain;

import com.stemlaur.anki.infrastructure.InMemoryCardProgressRepository;

import java.util.Optional;

public class CardProgressService {
    private final CardProgressRepository cardProgressRepository;

    public CardProgressService() {
        this.cardProgressRepository = new InMemoryCardProgressRepository();
    }

    public CardProgressService(final CardProgressRepository cardProgressRepository) {

        this.cardProgressRepository = cardProgressRepository;
    }

    public Optional<CardProgress> findByCardToStudyId(final String id) {

        return this.cardProgressRepository.findCardProgressById(id);
    }

    public void save(final CardProgress cardProgress) {
        this.cardProgressRepository.save(cardProgress);
    }
}
