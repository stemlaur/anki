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
package com.stemlaur.anki.application.controllers.study;

import com.stemlaur.anki.domain.study.CardDoesNotExistInTheSession;
import com.stemlaur.anki.domain.study.CardToStudy;
import com.stemlaur.anki.domain.study.SessionDoesNotExist;
import com.stemlaur.anki.domain.study.api.StudyDeck;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
class StudyController {
    private final StudyDeck deckStudyService;

    @ApiOperation(value = "Webservice to create a study session", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created and id returned"),
            @ApiResponse(code = 500, message = "An internal server error occured")
    })
    @PostMapping(path = "/sessions", consumes = "application/json", produces = "application/json")
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

    @ApiOperation(value = "Webservice to get next card to study", response = CardToStudy.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Session or deck not found"),
            @ApiResponse(code = 500, message = "An internal server error occured")
    })
    @GetMapping(path = "/sessions/{id}/nextCard", produces = "application/json")
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

    @ApiOperation(value = "Webservice to give it's opinion about a card", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully gave opinion"),
            @ApiResponse(code = 404, message = "Session or card not found"),
            @ApiResponse(code = 500, message = "An internal server error occured")
    })
    @PostMapping(path = "/sessions/{id}/opinion", consumes = "application/json", produces = "application/json")
    ResponseEntity<?> studyCard(@PathVariable("id") final String sessionId, @RequestBody final StudyCardRequest request) {
        try {
            this.deckStudyService.study(sessionId, request.getCardId(), request.getOpinion());
            return ResponseEntity.ok().build();
        } catch (SessionDoesNotExist | CardDoesNotExistInTheSession e) {
            return ResponseEntity.notFound().build();
        } catch (Exception other) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
