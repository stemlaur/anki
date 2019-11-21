package com.stemlaur.anki.domain.study;

import com.stemlaur.anki.infrastructure.InMemoryCardProgressRepository;

public class CardProgressService {
    private final CardProgressRepository cardProgressRepository;

    public CardProgressService() {
        this.cardProgressRepository = new InMemoryCardProgressRepository();
    }

    public CardProgressService(final CardProgressRepository cardProgressRepository) {

        this.cardProgressRepository = cardProgressRepository;
    }

    public CardProgress findByCardToStudyId(final String id) {
        return this.cardProgressRepository.findCardProgressById(id).orElse(CardProgress.init(id));
    }

    public void save(final CardProgress cardProgress) {
        this.cardProgressRepository.save(cardProgress);
    }
}
