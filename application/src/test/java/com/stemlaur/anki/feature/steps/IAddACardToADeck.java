package com.stemlaur.anki.feature.steps;

import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.String.format;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public final class IAddACardToADeck {
    private final MockMvc mockMvc;

    public IAddACardToADeck(final MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public void with(String deckId, Card card) throws Exception {
        this.mockMvc.perform(
                post(format("/api/decks/%s/card", deckId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(format("{ \"question\": \"%s\", \"answer\":\"%s\"}", card.getQuestion(), card.getAnswer()))
        )
                .andExpect(status().isOk())
                .andReturn();
    }

    @Value
    public static class Card {
        private final String question;
        private final String answer;
    }
}
