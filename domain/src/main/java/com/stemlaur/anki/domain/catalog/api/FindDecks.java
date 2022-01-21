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
package com.stemlaur.anki.domain.catalog.api;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FindDecks {
    Optional<DeckSnapshot> byId(final String deckId);

    Collection<DeckSnapshot> all();

    class DeckSnapshot {
        private final String id;
        private final String title;
        private final List<CardSnapshot> cards;

        public DeckSnapshot(final String id, final String title, final List<CardSnapshot> cards) {
            this.id = id;
            this.title = title;
            this.cards = cards;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public List<CardSnapshot> getCards() {
            return cards;
        }
    }

    class CardSnapshot {
        private final String question;
        private final String answer;

        public CardSnapshot(final String question, final String answer) {
            this.question = question;
            this.answer = answer;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }
    }
}
