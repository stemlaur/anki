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
package com.stemlaur.anki.domain.catalog;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CardShould {
    private static final int CARD_ID = 1;
    private static final int ANOTHER_CARD_ID = 2;

    @Test
    public void beIdentifiedAsAValueObject() {
        final Card firstCard = new Card(CARD_ID, new CardDetail("question 1", "answer 1"));
        final Card secondCard = new Card(CARD_ID, new CardDetail("question 1", "answer 1"));
        final Card differentCard = new Card(ANOTHER_CARD_ID, new CardDetail("question 2", "answer 2"));

        assertEquals(firstCard, secondCard);
        assertNotEquals(firstCard, differentCard);
        assertEquals(firstCard.hashCode(), secondCard.hashCode());
        assertNotEquals(firstCard.hashCode(), differentCard.hashCode());
    }
}
