package com.stemlaur.anki.rest.controllers.catalog;

final class DeckDTO {
    private final String id;
    private final String title;

    DeckDTO(final String id, final String title) {

        this.id = id;
        this.title = title;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final DeckDTO deckDTO = (DeckDTO) o;

        if (id != null ? !id.equals(deckDTO.id) : deckDTO.id != null) return false;
        return title != null ? title.equals(deckDTO.title) : deckDTO.title == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
