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
package com.stemlaur.anki.application.controllers.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * StudyCardRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-26T09:30:05.187773+01:00[Europe/Paris]")
public class StudyCardRequest {
    @JsonProperty("cardId")
    private String cardId;
    @JsonProperty("opinion")
    private OpinionEnum opinion;

    public StudyCardRequest cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    /**
     * Get cardId
     *
     * @return cardId
     */
    @ApiModelProperty(value = "")

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public StudyCardRequest opinion(OpinionEnum opinion) {
        this.opinion = opinion;
        return this;
    }

    /**
     * Get opinion
     *
     * @return opinion
     */
    @ApiModelProperty(value = "")

    public OpinionEnum getOpinion() {
        return opinion;
    }

    public void setOpinion(OpinionEnum opinion) {
        this.opinion = opinion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudyCardRequest studyCardRequest = (StudyCardRequest) o;
        return Objects.equals(this.cardId, studyCardRequest.cardId) &&
                Objects.equals(this.opinion, studyCardRequest.opinion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId, opinion);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class StudyCardRequest {\n");

        sb.append("    cardId: ").append(toIndentedString(cardId)).append("\n");
        sb.append("    opinion: ").append(toIndentedString(opinion)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    /**
     * Gets or Sets opinion
     */
    public enum OpinionEnum {
        GREEN("GREEN"),

        ORANGE("ORANGE"),

        RED("RED");

        private String value;

        OpinionEnum(String value) {
            this.value = value;
        }

        @JsonCreator
        public static OpinionEnum fromValue(String value) {
            for (OpinionEnum b : OpinionEnum.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}

