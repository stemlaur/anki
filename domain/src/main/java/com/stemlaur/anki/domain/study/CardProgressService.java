package com.stemlaur.anki.domain.study;

import com.stemlaur.anki.domain.study.spi.CardProgresses;
import com.stemlaur.livingdocumentation.annotation.DomainService;

/**
 * Service to retrieve the progress of a specific card.
 */
@DomainService
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
