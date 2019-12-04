package com.stemlaur.anki.feature.steps;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static java.lang.String.format;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public final class IStartAStudySession {
    private final MockMvc mockMvc;

    public IStartAStudySession(final MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public String with(String deckId) throws Exception {
        final MvcResult result = this.mockMvc.perform(
                post("/api/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(format("{ \"deckId\": \"%s\"}", deckId))
        )
                .andExpect(status().isCreated())
                .andReturn();
        return result.getResponse().getContentAsString();
    }
}
