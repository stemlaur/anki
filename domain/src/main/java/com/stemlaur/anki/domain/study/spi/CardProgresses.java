package com.stemlaur.anki.domain.study.spi;

import com.stemlaur.anki.domain.study.CardProgress;

import java.util.Optional;

public interface CardProgresses {
    Optional<CardProgress> findCardProgressById(String cardToStudyId);

    void save(CardProgress cardProgress);
}
