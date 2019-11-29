package com.stemlaur.anki.rest.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
final class AddCardRequest {
    @JsonProperty("question")
    private String question;
    @JsonProperty("answer")
    private String answer;
}
