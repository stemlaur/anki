package com.stemlaur.anki.domain.study;

import java.util.Optional;

public interface CardProgressRepository {
    Optional<CardProgress> findCardProgressById(String cardToStudyId);

    void save(CardProgress cardProgress);
}
