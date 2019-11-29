package com.stemlaur.anki.rest.controllers.catalog;

import com.stemlaur.anki.domain.catalog.CardDetail;
import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/decks")
@Slf4j
class DeckController {
    private final DeckService deckService;

    DeckController(final DeckService deckService) {
        this.deckService = deckService;
    }

    @GetMapping(path = "/", produces = "application/json")
    ResponseEntity<List<DeckDTO>> findAll() {
        try {
            return ResponseEntity.ok(
                    this.deckService.findAll().stream()
                            .map(deck -> new DeckDTO(deck.id(), deck.title()))
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    ResponseEntity<String> createDeck(@RequestBody final CreateDeckRequest request) {
        try {
            final String id = this.deckService.create(request.getTitle());
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(id)
                    .toUri();
            return ResponseEntity.created(location).body(id);
        } catch (final Exception e) {
            log.error("An error occured while creating a deck", e);
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping(path = "/{id}/card", consumes = "application/json", produces = "application/json")
    ResponseEntity<?> addCard(@PathVariable("id") final String deckId, @RequestBody final AddCardRequest addCardRequest) {
        try {
            this.deckService.addCard(deckId, new CardDetail(addCardRequest.getQuestion(), addCardRequest.getAnswer()));
            return ResponseEntity.ok().build();
        } catch (DeckService.DeckDoesNotExist deckDoesNotExist) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    ResponseEntity<?> findDeckById(@PathVariable("id") final String deckId) {
        final Optional<Deck> optionalDeckById = this.deckService.findDeckById(deckId);
        if (optionalDeckById.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            final Deck deck = optionalDeckById.orElseThrow();
            return ResponseEntity.ok().body(new DeckDTO(deck.id(), deck.title()));
        }
    }
}
