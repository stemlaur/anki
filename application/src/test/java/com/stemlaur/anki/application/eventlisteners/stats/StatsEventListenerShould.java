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
package com.stemlaur.anki.application.eventlisteners.stats;

import com.stemlaur.anki.domain.catalog.DeckCreated;
import com.stemlaur.anki.domain.catalog.DeckId;
import com.stemlaur.anki.domain.stats.DeckStatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StatsEventListenerShould {

    private StatsEventListener statsEventListener;

    @Mock
    private DeckStatsService deckStatsService;

    @BeforeEach
    public void setUp() {
        this.statsEventListener = new StatsEventListener(deckStatsService);
    }

    @Test
    public void delegateToDeckStatsService() {
        DeckCreated event = new DeckCreated(DeckId.from("any"), "title");
        this.statsEventListener.on(event);

        verify(this.deckStatsService, times(1)).incrementByOne(event);
    }
}
