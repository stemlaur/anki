package com.stemlaur.anki.rest.controllers.catalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
final class CreateDeckRequest {
    @JsonProperty("title")
    private String title;
}
