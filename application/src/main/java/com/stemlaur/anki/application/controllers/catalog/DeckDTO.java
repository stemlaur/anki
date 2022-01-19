/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
