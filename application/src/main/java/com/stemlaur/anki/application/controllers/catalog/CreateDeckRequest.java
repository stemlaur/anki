package com.stemlaur.anki.application.controllers.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;

final class CreateDeckRequest {
    private final String title;

    public CreateDeckRequest(@JsonProperty("title") final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
