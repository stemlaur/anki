package com.stemlaur.anki.feature.common;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static java.lang.String.format;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Scope("cucumber-glue")
public class DeckHttpClient {

    @Autowired
    private MockMvc mockMvc;

    public DeckHttpClient(final MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @SneakyThrows
    public String create(String title) {
        final MvcResult result = this.mockMvc.perform(
                post("/api/decks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(format("{ \"title\": \"%s\"}", title))
        )
                .andExpect(status().isCreated())
                .andReturn();
        return result.getResponse().getContentAsString();
    }

    @SneakyThrows
    public void addCard(final String deckId, final String question, final String answer) {
        this.mockMvc.perform(
                post(format("/api/decks/%s/card", deckId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(format("{ \"question\": \"%s\", \"answer\":\"%s\"}", question, answer))
        )
                .andExpect(status().isOk())
                .andReturn();
    }
}
