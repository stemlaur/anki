# Anki [![Build Status](https://travis-ci.org/stemlaur/anki.svg?branch=master)](https://travis-ci.org/stemlaur/anki)

Anki deck engine. Satisfying the need of 3 different type of actors:

- Contributor: create decks and add cards to it
- User manager: add contributors and students
- Student: study cards from a deck

## Deck Service

Used by the Contributors to create decks and add anki cards to it.

```java
public class DeckService {
    <?> create(<?> title, <?> contributorId); // returns the deckId
    void remove(<?> deckId, <?> contributorId);
    <?> findDeckBy(<?> deckId); // returns the deck and all its card
    <?> addCard(<?> cardDetail);
}
```

### Rules

- only the contributor which created the deck can delete it
- two decks with the same id cannot exist

## User Service

Used by user manager to add or delete contributors/students.

```java
public class UserService {
            
    void addContributor(<?> alias);
    void deleteContributor(<?> contributorId);

    void addStudent(<?> alias);
    void deleteStudent(<?> contributorId);
}
```

### Rules

- contributors and students should not be duplicated
- When deleting a contributor, all the decks associated should be deleted as well
- When deleting a employee, all the statistics about deck study associated should be deleted as well

## Deck study service

Allows students to study decks.

```java
public class DeckStudyService {
    <?> startStudySession(<?> studentId, <?> deckId); // returns a session containing all card of the deck
    <?> nextCardToStudy(<?> studentId, <?> sessionId); // returns the Card to study
    void study(<?> studentId, <?> sessionId, <?> deckId, <?> cardId, <?> opinion);
    void stopStudySession(<?> studentId, <?> sessionId);
    <?> getCurrentSessions(<?> studentId);
}
```

### Rules

- Student should exist to start a study session
- Deck should exist to start a study session
- A Session id should be unique and it should be limited in time (30 minutes)
- A session is composed of card progress of the cards from the deck when it is started.

- nextCardToStudy should return the next card to study if the session is not expired 
- the next card to study is determined by its duration before next evaluation
- if two cards have the same duration before next evaluation, it should be chosed randomly

- when the student studies a card, it provides an opinion represented by a color (GREEN, ORANGE, RED)
- the color of the opinion will influence the card progress
- to start with, the card progress has a 'duration before next evaluation' of '1' and a 'last evaluation at' now
- GREEN multiply the 'duration before next evaluation' to 3 
- ORANGE divides the 'duration before next evaluation' by 2
- RED divides the 'duration before next evaluation' by 3

