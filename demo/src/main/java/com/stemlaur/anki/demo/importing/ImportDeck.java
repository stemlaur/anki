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
package com.stemlaur.anki.demo.importing;

import com.stemlaur.anki.domain.catalog.CardDetail;
import com.stemlaur.anki.domain.catalog.DeckService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ImportDeck {
    private final DeckService deckService;
    private final String deckTitle;
    private final String csvFilePath;

    public ImportDeck(final DeckService deckService,
                      final String deckTitle,
                      final String csvFilePath) {
        this.deckService = deckService;
        this.deckTitle = deckTitle;
        this.csvFilePath = csvFilePath;
    }

    private static List<String> fromResourceToListOfString(final String csvFilePath) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(csvFilePath), StandardCharsets.UTF_8)) {
            return stream.collect(Collectors.toList());
        }
    }

    public String ofResource() throws IOException {
        final List<String> lines = fromResourceToListOfString(this.csvFilePath);
        final String deckId = this.deckService.create(this.deckTitle);
        lines.forEach(line -> createCardForDeck(deckId, line));
        return deckId;
    }

    private void createCardForDeck(final String deckId, final String line) {
        final String[] split = line.split("\\|");
        final CardDetail cardDetail = new CardDetail(split[0], split[1]);
        this.deckService.addCard(deckId, cardDetail);
    }
}
