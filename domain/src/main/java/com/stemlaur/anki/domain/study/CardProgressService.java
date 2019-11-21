package com.stemlaur.anki.domain.study;

public class CardProgressService {
    private final CardProgressRepository cardProgressRepository;

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
