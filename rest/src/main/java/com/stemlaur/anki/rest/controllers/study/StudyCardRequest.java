package com.stemlaur.anki.rest.controllers.study;

import com.stemlaur.anki.domain.study.Opinion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
final class StudyCardRequest {
    private String cardId;
    private Opinion opinion;
}
