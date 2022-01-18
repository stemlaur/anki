package com.stemlaur.anki.domain.catalog;

import com.stemlaur.anki.domain.common.DomainEvent;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class CardAdded implements DomainEvent {
    private final String deckId;
    private final int cardId;
    private final String question;
    private final String answer;

    public CardAdded(final DeckId deckId,
                     final int cardId,
                     final String question,
                     final String answer) {
        this.deckId = deckId.getValue();
        this.cardId = cardId;
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String getAggregateId() {
        return this.deckId;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CardAdded cardAdded = (CardAdded) o;
        return deckId.equals(cardAdded.deckId) && cardId == cardAdded.cardId && question.equals(cardAdded.question) && answer.equals(cardAdded.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deckId, cardId, question, answer);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("deckId", deckId)
                .append("cardId", cardId)
                .append("question", question)
                .append("answer", answer)
                .toString();
    }
}
