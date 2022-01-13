package com.stemlaur.anki.application.controllers.study;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

final class NextCardToStudyResponse {
    private String id;
    private String question;
    private String answer;

    private NextCardToStudyResponse() {
    }

    public NextCardToStudyResponse(@JsonProperty("id") final String id,
                                   @JsonProperty("question") final String question,
                                   @JsonProperty("answer") final String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final NextCardToStudyResponse that = (NextCardToStudyResponse) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(question, that.question)) return false;
        return Objects.equals(answer, that.answer);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }
}
