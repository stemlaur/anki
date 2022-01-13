package com.stemlaur.anki.application.controllers.study;

import com.fasterxml.jackson.annotation.JsonProperty;

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

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (question != null ? !question.equals(that.question) : that.question != null) return false;
        return answer != null ? answer.equals(that.answer) : that.answer == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }
}
