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
