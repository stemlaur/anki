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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stemlaur.anki.domain.study.Opinion;

final class StudyCardRequest {
    private final String cardId;
    private final Opinion opinion;

    public StudyCardRequest(@JsonProperty("cardId") final String cardId,
                            @JsonProperty("opinion") final Opinion opinion) {
        this.cardId = cardId;
        this.opinion = opinion;
    }

    public String getCardId() {
        return cardId;
    }

    public Opinion getOpinion() {
        return opinion;
    }
}
