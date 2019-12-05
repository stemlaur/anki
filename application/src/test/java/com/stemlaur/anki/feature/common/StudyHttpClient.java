package com.stemlaur.anki.feature.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static java.lang.String.format;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Scope("cucumber-glue")
public class StudyHttpClient {
    @Autowired
    private MockMvc mockMvc;

    public StudyHttpClient(final MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @SneakyThrows
    public String create(final String deckId) {
        final MvcResult result = this.mockMvc.perform(
                post("/api/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(format("{ \"deckId\": \"%s\"}", deckId))
        )
                .andExpect(status().isCreated())
                .andReturn();
        return result.getResponse().getContentAsString();
    }

    @SneakyThrows
    public CardToStudy nextCard(final String sessionId) {
        final MvcResult result = this.mockMvc.perform(
                get(format("/api/sessions/%s/nextCard", sessionId))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();
        return from(result.getResponse().getContentAsString());
    }

    private static CardToStudy from(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(body, CardToStudy.class);
    }

    @SneakyThrows
    public void study(final String sessionId, final String cardToStudyId, final String opinion) {
        this.mockMvc.perform(
                post(format("/api/sessions/%s/opinion", sessionId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(format("{ \"cardId\": \"%s\", \"opinion\": \"%s\"}", cardToStudyId, opinion))
        )
                .andExpect(status().isOk());
    }

    @Data
    public static class CardToStudy {
        private String id;
        private String question;
        private String answer;
    }
}
