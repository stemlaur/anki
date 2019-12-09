package com.stemlaur.anki.feature.common;

import lombok.Data;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Data
@Scope("cucumber-glue")
public class World {
    private String deckId;
    private String sessionId;

    private Stack<StudyHttpClient.CardToStudy> cardsToStudies = new Stack<>();

    public void pushCardToStudy(StudyHttpClient.CardToStudy cardToStudy) {
        this.cardsToStudies.push(cardToStudy);
    }

    public StudyHttpClient.CardToStudy popCardToStudy() {
        return this.cardsToStudies.pop();
    }

    public StudyHttpClient.CardToStudy peekCardToStudy() {
        return this.cardsToStudies.peek();
    }

    public List<StudyHttpClient.CardToStudy> getListOfCardsToStudy() {
        return new ArrayList<>(this.cardsToStudies);
    }

}
