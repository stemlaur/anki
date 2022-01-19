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

import com.stemlaur.anki.domain.study.CardProgress;
import com.stemlaur.anki.domain.study.spi.fake.InMemoryCardProgresses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryCardProgressesShould {
    private static final String CARD_ID = "b56a78e1-cd2a-4b6b-b7af-d38cfdaef557";
    private InMemoryCardProgresses inMemoryCardProgresses;

    @BeforeEach
    public void setUp() {
        this.inMemoryCardProgresses = new InMemoryCardProgresses();
    }

    @Test
    public void saveCardProgress() {
        assertTrue(this.inMemoryCardProgresses.findCardProgressById(CARD_ID).isEmpty());

        this.inMemoryCardProgresses.save(CardProgress.init(CARD_ID));

        assertTrue(this.inMemoryCardProgresses.findCardProgressById(CARD_ID).isPresent());
    }
}
