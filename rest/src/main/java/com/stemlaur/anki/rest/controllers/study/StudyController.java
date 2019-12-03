package com.stemlaur.anki.rest.controllers.study;

import com.stemlaur.anki.domain.study.CardToStudy;
import com.stemlaur.anki.domain.study.DeckStudyService;
import com.stemlaur.anki.domain.study.DeckStudyService.CardDoesNotExistInTheSession;
import com.stemlaur.anki.domain.study.DeckStudyService.SessionDoesNotExist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping(path = "/studies")
@Slf4j
class StudyController {
    private final DeckStudyService deckStudyService;

    StudyController(final DeckStudyService deckStudyService) {

        this.deckStudyService = deckStudyService;
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    ResponseEntity<String> createStudySession(@RequestBody final CreateStudySessionRequest request) {
        try {
            final String id = this.deckStudyService.startStudySession(request.getDeckId());
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(id)
                    .toUri();
            return ResponseEntity.created(location).body(id);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(path = "/{id}/nextCard", produces = "application/json")
    ResponseEntity<?> nextCardToStudy(@PathVariable("id") final String sessionId) {
        try {
            final Optional<CardToStudy> optionalCardToStudy = this.deckStudyService.nextCardToStudy(sessionId);
            if (optionalCardToStudy.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            final CardToStudy cardToStudy = optionalCardToStudy.orElseThrow();
            return ResponseEntity.ok().body(
                    new NextCardToStudyResponse(cardToStudy.id(), cardToStudy.question(), cardToStudy.answer()));
        } catch (SessionDoesNotExist e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(path = "/{id}/opinion", consumes = "application/json", produces = "application/json")
    ResponseEntity<?> studyCard(@RequestBody final StudyCardRequest request) {
        try {
            this.deckStudyService.study(request.getSessionId(), request.getCardId(), request.getOpinion());
            return ResponseEntity.ok().build();
        } catch (SessionDoesNotExist | CardDoesNotExistInTheSession e) {
            return ResponseEntity.notFound().build();
        } catch (Exception other) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
