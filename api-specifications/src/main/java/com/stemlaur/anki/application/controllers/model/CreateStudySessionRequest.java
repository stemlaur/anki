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

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * CreateStudySessionRequest
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-26T09:30:05.187773+01:00[Europe/Paris]")
public class CreateStudySessionRequest {
    @JsonProperty("deckId")
    private String deckId;

    public CreateStudySessionRequest deckId(String deckId) {
        this.deckId = deckId;
        return this;
    }

    /**
     * Get deckId
     *
     * @return deckId
     */
    @ApiModelProperty(value = "")

    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreateStudySessionRequest createStudySessionRequest = (CreateStudySessionRequest) o;
        return Objects.equals(this.deckId, createStudySessionRequest.deckId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deckId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CreateStudySessionRequest {\n");

        sb.append("    deckId: ").append(toIndentedString(deckId)).append("\n");
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
}

