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

import com.stemlaur.anki.application.controllers.model.AddCardRequest;
import com.stemlaur.anki.application.controllers.model.CreateDeckRequest;
import com.stemlaur.anki.application.controllers.model.DeckDTO;
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
@Api(value = "decks", description = "the decks API")
public interface DecksApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /decks/{id}/card : Add a card to an existing deck
     *
     * @param id The id of the deck (required)
     * @param addCardRequest Add card request object (required)
     * @return Successfully added the card (status code 200)
     *         or Created (status code 201)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Deck not found (status code 404)
     *         or An internal server error occured (status code 500)
     */
    @ApiOperation(value = "Add a card to an existing deck", nickname = "addCardToDeck", notes = "", response = Object.class, tags={ "deck", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully added the card", response = Object.class),
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Deck not found"),
        @ApiResponse(code = 500, message = "An internal server error occured") })
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/decks/{id}/card",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<Object> _addCardToDeck(@ApiParam(value = "The id of the deck", required = true) @PathVariable("id") String id,@ApiParam(value = "Add card request object", required = true) @Valid @RequestBody AddCardRequest addCardRequest) {
        return addCardToDeck(id, addCardRequest);
    }

    // Override this method
    default  ResponseEntity<Object> addCardToDeck(String id, AddCardRequest addCardRequest) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /decks : Create a deck
     *
     * @param request Create deck request object (required)
     * @return OK (status code 200)
     *         or Successfully created and id returned (status code 201)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or An internal server error occured (status code 500)
     */
    @ApiOperation(value = "Create a deck", nickname = "createDeck", notes = "", response = String.class, tags={ "deck", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = String.class),
        @ApiResponse(code = 201, message = "Successfully created and id returned", response = String.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "An internal server error occured") })
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/decks",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<String> _createDeck(@ApiParam(value = "Create deck request object", required = true) @Valid @RequestBody CreateDeckRequest request) {
        return createDeck(request);
    }

    // Override this method
    default  ResponseEntity<String> createDeck(CreateDeckRequest request) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /decks/{id} : Find an existing deck by its id
     *
     * @param id The id of the deck (required)
     * @return Successfully found (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Deck not found (status code 404)
     *         or An internal server error occurred (status code 500)
     */
    @ApiOperation(value = "Find an existing deck by its id", nickname = "findDeckById", notes = "", response = DeckDTO.class, tags={ "deck", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully found", response = DeckDTO.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Deck not found"),
        @ApiResponse(code = 500, message = "An internal server error occurred") })
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/decks/{id}",
        produces = { "application/json" }
    )
    default ResponseEntity<DeckDTO> _findDeckById(@ApiParam(value = "The id of the deck", required = true) @PathVariable("id") String id) {
        return findDeckById(id);
    }

    // Override this method
    default  ResponseEntity<DeckDTO> findDeckById(String id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"id\" : \"id\", \"title\" : \"title\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /decks : List all existing decks
     *
     * @return Successfully retrieved list (status code 200)
     *         or Unauthorized (status code 401)
     *         or Forbidden (status code 403)
     *         or Not Found (status code 404)
     *         or An internal server error occured (status code 500)
     */
    @ApiOperation(value = "List all existing decks", nickname = "listDecks", notes = "", response = DeckDTO.class, responseContainer = "List", tags={ "deck", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully retrieved list", response = DeckDTO.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "An internal server error occured") })
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/decks",
        produces = { "application/json" }
    )
    default ResponseEntity<List<DeckDTO>> _listDecks() {
        return listDecks();
    }

    // Override this method
    default  ResponseEntity<List<DeckDTO>> listDecks() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"id\" : \"id\", \"title\" : \"title\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
