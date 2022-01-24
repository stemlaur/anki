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
package com.stemlaur.anki.domain.stats;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TotalNumberOfCreatedDecksShould {
    @Test
    void beAValueObject() {
        TotalNumberOfCreatedDecks first = new TotalNumberOfCreatedDecks(12);
        TotalNumberOfCreatedDecks anotherSameAsFirst = new TotalNumberOfCreatedDecks(12);
        TotalNumberOfCreatedDecks second = new TotalNumberOfCreatedDecks(55);

        assertEquals(first, anotherSameAsFirst);
        assertNotEquals(first, second);

        assertEquals(first.hashCode(), first.hashCode());
        assertNotEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void forcePositiveValue() {
        NumberOfDecksNotPositive numberOfDecksNotPositive = assertThrows(NumberOfDecksNotPositive.class, () -> new TotalNumberOfCreatedDecks(-1));

        assertThat(numberOfDecksNotPositive.getMessage()).isEqualTo("Value '-1' should be >= 0");
    }
}
