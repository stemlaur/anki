package com.stemlaur.anki.domain;

import java.util.Optional;

public interface CardProgressRepository {
    Optional<CardProgress> findCardProgressById(String cardToStudyId);

    void save(CardProgress cardProgress);
}
