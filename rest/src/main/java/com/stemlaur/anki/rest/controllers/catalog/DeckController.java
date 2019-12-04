package com.stemlaur.anki.rest.controllers.catalog;

import com.stemlaur.anki.domain.catalog.CardDetail;
import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api")
@Slf4j
public class DeckController {
    private final DeckService deckService;

    DeckController(final DeckService deckService) {
        this.deckService = deckService;
    }

    @ApiOperation(value = "Webservice to find all existing decks", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 500, message = "An internal server error occured")
    })
    @GetMapping(path = "/decks", produces = "application/json")
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

    @ApiOperation(value = "Webservice to create a deck", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created and id returned"),
            @ApiResponse(code = 500, message = "An internal server error occured")
    })
    @PostMapping(path = "/decks", consumes = "application/json", produces = "application/json")
    ResponseEntity<String> createDeck(
            @ApiParam(value = "Create deck request object", required = true)
            @RequestBody final CreateDeckRequest request) {
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

    @ApiOperation(value = "Webservice to add a card to an existing deck", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added the card"),
            @ApiResponse(code = 404, message = "Deck not found"),
            @ApiResponse(code = 500, message = "An internal server error occured")
    })
    @PostMapping(path = "/decks/{id}/card", consumes = "application/json", produces = "application/json")
    ResponseEntity<?> addCard(
            @ApiParam(value = "The id of the deck", required = true) @PathVariable("id") final String deckId,
            @ApiParam(value = "Add card request object", required = true) @RequestBody final AddCardRequest addCardRequest) {
        try {
            this.deckService.addCard(deckId, new CardDetail(addCardRequest.getQuestion(), addCardRequest.getAnswer()));
            return ResponseEntity.ok().build();
        } catch (DeckService.DeckDoesNotExist deckDoesNotExist) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @ApiOperation(value = "Webservice to find an existing deck by its id", response = Deck.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found"),
            @ApiResponse(code = 404, message = "Deck not found"),
            @ApiResponse(code = 500, message = "An internal server error occured")
    })
    @GetMapping(path = "/decks/{id}", produces = "application/json")
    ResponseEntity<?> findDeckById(
            @ApiParam(value = "The id of the deck", required = true) @PathVariable("id") final String deckId) {
        try {
            final Optional<Deck> optionalDeckById = this.deckService.findDeckById(deckId);
            if (optionalDeckById.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                final Deck deck = optionalDeckById.orElseThrow();
                return ResponseEntity.ok().body(new DeckDTO(deck.id(), deck.title()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
