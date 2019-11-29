package com.stemlaur.anki.rest.controllers;

import com.stemlaur.anki.domain.catalog.DeckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
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
    List<DeckDTO> findAll() {
        return this.deckService.findAll().stream()
                .map(deck -> new DeckDTO(deck.id(), deck.title()))
                .collect(Collectors.toList());
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
}
