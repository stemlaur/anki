package com.stemlaur.anki.domain;

import com.stemlaur.anki.infrastructure.InMemorySessionRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class DeckStudyService {
    private final DeckService deckService;
    private final SessionIdFactory sessionIdFactory;
    private final SessionRepository sessionRepository;

    public DeckStudyService(final DeckService deckService,
                            final SessionIdFactory sessionIdFactory,
                            final SessionRepository sessionRepository) {
        this.deckService = deckService;
        this.sessionIdFactory = sessionIdFactory;
        this.sessionRepository = sessionRepository;
    }

    public DeckStudyService(final DeckService deckService,
                            final SessionIdFactory sessionIdFactory) {
        this.deckService = deckService;
        this.sessionIdFactory = sessionIdFactory;
        this.sessionRepository = new InMemorySessionRepository();
    }

    public String startStudySession(final String deckId) {
        final String sessionId = this.sessionIdFactory.create();
        final Deck deck = this.deckService.findDeckById(deckId).orElseThrow(DeckDoesNotExist::new);
        if (deck.cards().isEmpty()) {
            throw new DeckDoesNotContainAnyCards();
        }

        this.sessionRepository.save(new Session(sessionId,
                this.convertCardsToCardsToStudy(deck)));
        return sessionId;
    }

    private Set<CardToStudy> convertCardsToCardsToStudy(final Deck deck) {
        return deck.cards().stream()
                .map(c -> new CardToStudy(UUID.randomUUID().toString(), c.detail().question()))
                .collect(Collectors.toSet());
    }

    public Optional<CardToStudy> nextCardToStudy(final String sessionId) {
        final Session session = this.sessionRepository.findById(sessionId).orElseThrow(SessionDoesNotExist::new);
        return session.cardsToStudy().stream().findFirst();
    }

    public void study(final String sessionId, final String cardId, final Opinion opinion) {

    }


    //@formatter:off
    public static class DeckDoesNotExist extends RuntimeException { }
    public static class DeckDoesNotContainAnyCards extends RuntimeException{}
    public static class SessionDoesNotExist extends RuntimeException { }
    //@formatter:on
}
