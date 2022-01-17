package com.stemlaur.anki.application.eventlisteners.stats;

import com.stemlaur.anki.domain.catalog.DeckCreated;
import com.stemlaur.anki.domain.stats.DeckStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StatsEventListener {

    private final DeckStatsService deckStatsService;

    @EventListener
    public void on(final DeckCreated event) {
        deckStatsService.incrementByOne(event);
    }
}
