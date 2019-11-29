package com.stemlaur.anki.rest.controllers;

import lombok.Value;

@Value
final class DeckDTO {
    private final String id;
    private final String title;

    DeckDTO(final String id, final String title) {

        this.id = id;
        this.title = title;
    }
}
