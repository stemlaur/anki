package com.stemlaur.anki.application.controllers.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

final class DeckDTO {
    public String id;
    public String title;

    private DeckDTO() {

    }

    DeckDTO(@JsonProperty("id") final String id,
            @JsonProperty("title") final String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final DeckDTO deckDTO = (DeckDTO) o;

        if (!Objects.equals(id, deckDTO.id)) return false;
        return Objects.equals(title, deckDTO.title);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
