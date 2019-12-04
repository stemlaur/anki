package com.stemlaur.anki.feature.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static java.lang.String.format;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public final class IAskForNextCardToStudy {
    private final MockMvc mockMvc;

    public IAskForNextCardToStudy(final MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public CardToStudy with(String sessionId) throws Exception {
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

    @Data
    public static class CardToStudy {
        private String id;
        private String question;
        private String answer;
    }
}
