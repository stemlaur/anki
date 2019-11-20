package com.stemlaur.anki.domain.study;

import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.common.Clock;
import com.stemlaur.anki.infrastructure.InMemorySessionRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class DeckStudyService {
    private final DeckService deckService;
    private final SessionIdFactory sessionIdFactory;
    private final SessionRepository sessionRepository;
    private final CardProgressService cardProgressService;
    private final Clock clock;

    public DeckStudyService(final DeckService deckService,
                            final CardProgressService cardProgressService,
                            final SessionIdFactory sessionIdFactory,
                            final SessionRepository sessionRepository,
                            final Clock clock) {
        this.deckService = deckService;
        this.sessionIdFactory = sessionIdFactory;
        this.sessionRepository = sessionRepository;
        this.cardProgressService = cardProgressService;
        this.clock = clock;
    }

    public DeckStudyService(final DeckService deckService, final CardProgressService cardProgressService, final Clock clock) {
        this(deckService, cardProgressService, new SessionIdFactory(), new InMemorySessionRepository(), clock);
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
        final Session session = this.sessionRepository.findById(sessionId).orElseThrow(SessionDoesNotExist::new);
        final CardToStudy card = session.findCard(cardId).orElseThrow(CardDoesNotExistInTheSession::new);
        final CardProgress cardProgress =
                this.cardProgressService.findByCardToStudyId(card.id()).orElse(CardProgress.init(card.id()));
        cardProgress.updateProgress(opinion, this.clock.now());
        this.cardProgressService.save(cardProgress);
    }


    //@formatter:off
    public static class DeckDoesNotExist extends RuntimeException { }
    public static class DeckDoesNotContainAnyCards extends RuntimeException{}
    public static class SessionDoesNotExist extends RuntimeException { }
    public static class CardDoesNotExistInTheSession extends RuntimeException { }
    //@formatter:on
}
