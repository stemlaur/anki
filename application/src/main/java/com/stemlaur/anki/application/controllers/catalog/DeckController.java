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

import com.stemlaur.anki.application.controllers.api.DecksApi;
import com.stemlaur.anki.application.controllers.model.AddCardRequest;
import com.stemlaur.anki.application.controllers.model.CreateDeckRequest;
import com.stemlaur.anki.application.controllers.model.DeckDTO;
import com.stemlaur.anki.domain.catalog.CardDetail;
import com.stemlaur.anki.domain.catalog.DeckDoesNotExist;
import com.stemlaur.anki.domain.catalog.api.AddCard;
import com.stemlaur.anki.domain.catalog.api.CreateDeck;
import com.stemlaur.anki.domain.catalog.api.FindDecks;
import com.stemlaur.anki.domain.catalog.api.FindDecks.DeckSnapshot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
class DeckController implements DecksApi {
    private final CreateDeck createDeck;
    private final AddCard addCard;
    private final FindDecks findDecks;

    @Override
    public ResponseEntity<List<DeckDTO>> listDecks() {
        try {
            return ResponseEntity.ok(this.findDecks.all().stream().map(deck -> new DeckDTO().id(deck.getId()).title(deck.getTitle())).collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<String> createDeck(final CreateDeckRequest request) {
        try {
            final String id = this.createDeck.create(request.getTitle());
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
            return ResponseEntity.created(location).body(id);
        } catch (final Exception e) {
            log.error("An error occured while creating a deck", e);
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<Object> addCardToDeck(final String deckId, final AddCardRequest addCardRequest) {
        try {
            this.addCard.addCard(deckId, new CardDetail(addCardRequest.getQuestion(), addCardRequest.getAnswer()));
            return ResponseEntity.ok().build();
        } catch (DeckDoesNotExist deckDoesNotExist) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ResponseEntity<DeckDTO> findDeckById(final String deckId) {
        try {
            final Optional<DeckSnapshot> optionalDeckById = this.findDecks.byId(deckId);
            if (optionalDeckById.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                final DeckSnapshot deck = optionalDeckById.orElseThrow();
                return ResponseEntity.ok().body(new DeckDTO().id(deck.getId()).title(deck.getTitle()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
