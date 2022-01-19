/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
