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
package com.stemlaur.anki.domain.study.fake;

import com.stemlaur.anki.domain.study.CardToStudy;
import com.stemlaur.anki.domain.study.Session;
import com.stemlaur.anki.domain.study.spi.fake.InMemorySessions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemorySessionsShould {
    private static final String SESSION_ID = "0ae41528-e41d-4737-af4a-3858afd71036";
    private static final String CARD_ID = "25cfd613-a150-4cb9-bc21-6a5cc8871c87";
    private static final String QUESTION = "What is the ocipital lobe involved in ?";
    private static final String ANSWER = "Visual perception.";

    private InMemorySessions inMemorySessions;

    @BeforeEach
    public void setUp() {
        this.inMemorySessions = new InMemorySessions();
    }

    @Test
    public void saveSession() {
        assertTrue(this.inMemorySessions.find(SESSION_ID).isEmpty());

        this.inMemorySessions.save(new Session(
                SESSION_ID,
                Collections.singleton(new CardToStudy(CARD_ID, QUESTION, ANSWER))));

        assertTrue(this.inMemorySessions.find(SESSION_ID).isPresent());
    }
}
