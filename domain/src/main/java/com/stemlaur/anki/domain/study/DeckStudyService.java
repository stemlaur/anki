package com.stemlaur.anki.domain.study;

import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.common.Clock;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static java.util.Optional.empty;

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

    public String startStudySession(final String deckId) {
        final String sessionId = this.sessionIdFactory.create();
        final Deck deck = this.findDeck(deckId);
        this.sessionRepository.save(new Session(sessionId, this.convertCardsToCardsToStudy(deck)));
        return sessionId;
    }

    private Deck findDeck(final String deckId) {
        final Deck deck = this.deckService.findDeckById(deckId).orElseThrow(DeckDoesNotExist::new);
        if (deck.cards().isEmpty()) {
            throw new DeckDoesNotContainAnyCards();
        }
        return deck;
    }

    private Set<CardToStudy> convertCardsToCardsToStudy(final Deck deck) {
        return deck.cards().stream()
                .map(c -> new CardToStudy(UUID.randomUUID().toString(), c.detail().question(), c.detail().answer()))
                .collect(Collectors.toSet());
    }

    public Optional<CardToStudy> nextCardToStudy(final String sessionId) {
        final Session session = this.sessionRepository.findById(sessionId).orElseThrow(SessionDoesNotExist::new);
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
        final Session session = this.sessionRepository.findById(sessionId).orElseThrow(SessionDoesNotExist::new);
        final CardToStudy card = session.findCard(cardId).orElseThrow(CardDoesNotExistInTheSession::new);
        final CardProgress cardProgress = this.cardProgressService.findByCardToStudyId(card.id());
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
