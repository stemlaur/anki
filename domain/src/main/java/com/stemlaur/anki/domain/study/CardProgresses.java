package com.stemlaur.anki.domain.study;

import java.util.Optional;

public interface CardProgresses {
    Optional<CardProgress> findCardProgressById(String cardToStudyId);

    void save(CardProgress cardProgress);
}
