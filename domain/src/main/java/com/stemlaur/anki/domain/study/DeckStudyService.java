package com.stemlaur.anki.domain.study;

import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.study.api.StudyDeck;
import com.stemlaur.anki.domain.study.spi.Sessions;
import com.stemlaur.livingdocumentation.annotation.DomainService;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static java.util.Optional.empty;

/**
 * Service to study decks.
 */
@DomainService
public class DeckStudyService implements StudyDeck {
    private final DeckService deckService;
    private final SessionIdFactory sessionIdFactory;
    private final Sessions sessions;
    private final CardProgressService cardProgressService;
    private final Clock clock;

    public DeckStudyService(final DeckService deckService,
                            final CardProgressService cardProgressService,
                            final SessionIdFactory sessionIdFactory,
                            final Sessions sessions,
                            final Clock clock) {
        this.deckService = deckService;
        this.sessionIdFactory = sessionIdFactory;
        this.sessions = sessions;
        this.cardProgressService = cardProgressService;
        this.clock = clock;
    }

    public String startStudySession(final String deckId) {
        final String sessionId = this.sessionIdFactory.create();
        final Deck deck = this.findDeck(deckId);
        this.sessions.save(new Session(sessionId, this.convertCardsToCardsToStudy(deck)));
        return sessionId;
    }

    private Deck findDeck(final String deckId) {
        final Deck deck = this.deckService.byId(deckId).orElseThrow(() -> new DeckDoesNotExist(deckId));
        if (deck.cards().isEmpty()) {
            throw new DeckDoesNotContainAnyCards(deckId);
        }
        return deck;
    }

    private Set<CardToStudy> convertCardsToCardsToStudy(final Deck deck) {
        return deck.cards().stream()
                .map(c -> new CardToStudy(UUID.randomUUID().toString(), c.detail().question(), c.detail().answer()))
                .collect(Collectors.toSet());
    }

    public Optional<CardToStudy> nextCardToStudy(final String sessionId) {
        final Session session = this.sessions.find(sessionId).orElseThrow(() -> new SessionDoesNotExist(sessionId));
        final Optional<CardProgress> randomCardProgressWithMinimalScore =
                this.findRadomCardProgressWithLowestScore(session.cardsToStudy());
        if (randomCardProgressWithMinimalScore.isEmpty()) {
            return empty();
        }
        return getCardToStudyById(session.cardsToStudy(), randomCardProgressWithMinimalScore.orElseThrow().id());
    }

    private Optional<CardProgress> findRadomCardProgressWithLowestScore(final Set<CardToStudy> cardsToStudy) {
        final List<CardProgress> cardProgressesSortedByScoreAsc = this.findCardProgressesSortedByScoreAsc(cardsToStudy);
        if (cardProgressesSortedByScoreAsc.isEmpty()) {
            return empty();
        }
        return Optional.of(this.findRandomCardProgressWithScore(cardProgressesSortedByScoreAsc,
                cardProgressesSortedByScoreAsc.get(0).score()));
    }

    private Optional<CardToStudy> getCardToStudyById(final Set<CardToStudy> cardsToStudy,
                                                     final String cardId) {
        return cardsToStudy.stream()
                .filter(c -> cardId.equals(c.id()))
                .findFirst();
    }

    private CardProgress findRandomCardProgressWithScore(final List<CardProgress> progresses,
                                                         final Score score) {
        return progresses.stream()
                .filter(cardProgress -> cardProgress.score().equals(score))
                .min((o1, o2) -> ThreadLocalRandom.current().nextInt(-1, 2))
                .orElseThrow();
    }

    private List<CardProgress> findCardProgressesSortedByScoreAsc(final Set<CardToStudy> cardsToStudy) {
        return cardsToStudy.stream()
                .map(c -> this.cardProgressService.findByCardToStudyId(c.id()))
                .sorted(Comparator.comparingInt(o -> o.score().value()))
                .collect(Collectors.toList());
    }

    public void study(final String sessionId, final String cardId, final Opinion opinion) {
        final Session session = this.sessions.find(sessionId).orElseThrow(() -> new SessionDoesNotExist(sessionId));
        final CardToStudy card = session.findCard(cardId).orElseThrow(() -> new CardDoesNotExistInTheSession(sessionId, cardId));
        final CardProgress cardProgress = this.cardProgressService.findByCardToStudyId(card.id());
        cardProgress.updateProgress(opinion, LocalDateTime.now(clock));
        this.cardProgressService.save(cardProgress);
    }

    //@formatter:on
}
