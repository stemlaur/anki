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

import com.stemlaur.anki.application.controllers.api.SessionsApi;
import com.stemlaur.anki.application.controllers.model.CreateStudySessionRequest;
import com.stemlaur.anki.application.controllers.model.NextCardToStudyResponse;
import com.stemlaur.anki.application.controllers.model.StudyCardRequest;
import com.stemlaur.anki.domain.study.CardDoesNotExistInTheSession;
import com.stemlaur.anki.domain.study.CardToStudy;
import com.stemlaur.anki.domain.study.Opinion;
import com.stemlaur.anki.domain.study.SessionDoesNotExist;
import com.stemlaur.anki.domain.study.api.StudyDeck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
class StudyController implements SessionsApi {
    private final StudyDeck deckStudyService;

    @Override
    public ResponseEntity<String> createStudySession(final CreateStudySessionRequest request) {
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

    @Override
    public ResponseEntity<NextCardToStudyResponse> nextCardToStudy(final String sessionId) {
        try {
            final Optional<CardToStudy> optionalCardToStudy = this.deckStudyService.nextCardToStudy(sessionId);
            if (optionalCardToStudy.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            final CardToStudy cardToStudy = optionalCardToStudy.orElseThrow();
            return ResponseEntity.ok().body(
                    new NextCardToStudyResponse().id(cardToStudy.id()).question(cardToStudy.question()).answer(cardToStudy.answer()));
        } catch (SessionDoesNotExist e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Object> studyCard(final String sessionId, final StudyCardRequest request) {
        try {
            final Opinion opinion = Opinion.valueOf(request.getOpinion().name());
            this.deckStudyService.study(sessionId, request.getCardId(), opinion);
            return ResponseEntity.ok().build();
        } catch (SessionDoesNotExist | CardDoesNotExistInTheSession e) {
            return ResponseEntity.notFound().build();
        } catch (Exception other) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
