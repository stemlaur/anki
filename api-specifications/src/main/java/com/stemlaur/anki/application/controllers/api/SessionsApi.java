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
/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.3.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.stemlaur.anki.application.controllers.api;

import com.stemlaur.anki.application.controllers.model.CreateStudySessionRequest;
import com.stemlaur.anki.application.controllers.model.NextCardToStudyResponse;
import com.stemlaur.anki.application.controllers.model.StudyCardRequest;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-01-26T15:16:15.808817700+01:00[Europe/Paris]")
@Validated
@Api(value = "sessions", description = "the sessions API")
public interface SessionsApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /sessions : Create a study session
     *
     * @param request request (required)
     * @return OK (status code 200)
     *         or Successfully created and id returned (status code 201)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or An internal server error occured (status code 500)
     */
    @ApiOperation(value = "Create a study session", nickname = "createStudySession", notes = "", response = String.class, tags={ "study", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = String.class),
        @ApiResponse(code = 201, message = "Successfully created and id returned", response = String.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "An internal server error occured") })
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/sessions",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<String> _createStudySession(@ApiParam(value = "request", required = true) @Valid @RequestBody CreateStudySessionRequest request) {
        return createStudySession(request);
    }

    // Override this method
    default  ResponseEntity<String> createStudySession(CreateStudySessionRequest request) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /sessions/{id}/nextCard : Get next card to study
     *
     * @param id id (required)
     * @return Successful (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Session or deck not found (status code 404)
     *         or An internal server error occured (status code 500)
     */
    @ApiOperation(value = "Get next card to study", nickname = "nextCardToStudy", notes = "", response = NextCardToStudyResponse.class, tags={ "study", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful", response = NextCardToStudyResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Session or deck not found"),
        @ApiResponse(code = 500, message = "An internal server error occured") })
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/sessions/{id}/nextCard",
        produces = { "application/json" }
    )
    default ResponseEntity<NextCardToStudyResponse> _nextCardToStudy(@ApiParam(value = "id", required = true) @PathVariable("id") String id) {
        return nextCardToStudy(id);
    }

    // Override this method
    default  ResponseEntity<NextCardToStudyResponse> nextCardToStudy(String id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"answer\" : \"answer\", \"question\" : \"question\", \"id\" : \"id\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /sessions/{id}/opinion : Give it&#39;s opinion about a card
     *
     * @param id id (required)
     * @param request request (required)
     * @return Successfully gave opinion (status code 200)
     *         or Created (status code 201)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Session or card not found (status code 404)
     *         or An internal server error occured (status code 500)
     */
    @ApiOperation(value = "Give it's opinion about a card", nickname = "studyCard", notes = "", response = Object.class, tags={ "study", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully gave opinion", response = Object.class),
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Session or card not found"),
        @ApiResponse(code = 500, message = "An internal server error occured") })
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/sessions/{id}/opinion",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<Object> _studyCard(@ApiParam(value = "id", required = true) @PathVariable("id") String id,@ApiParam(value = "request", required = true) @Valid @RequestBody StudyCardRequest request) {
        return studyCard(id, request);
    }

    // Override this method
    default  ResponseEntity<Object> studyCard(String id, StudyCardRequest request) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
