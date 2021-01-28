package com.stemlaur.anki.domain.study;

public class CardProgressService {
    private final CardProgresses cardProgresses;

    public CardProgressService(final CardProgresses cardProgresses) {

        this.cardProgresses = cardProgresses;
    }

    public CardProgress findByCardToStudyId(final String id) {
        return this.cardProgresses.findCardProgressById(id).orElse(CardProgress.init(id));
    }

    public void save(final CardProgress cardProgress) {
        this.cardProgresses.save(cardProgress);
    }
}
