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
package com.stemlaur.anki.demo;

import com.stemlaur.anki.demo.importing.ImportDeck;
import com.stemlaur.anki.demo.menus.MainMenu;
import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.catalog.spi.fake.InMemoryDecks;
import com.stemlaur.anki.domain.catalog.spi.fake.SimpleDeckIdFactory;
import com.stemlaur.anki.domain.common.spi.fake.FakeDomainEvents;
import com.stemlaur.anki.domain.study.CardProgressService;
import com.stemlaur.anki.domain.study.DeckStudyService;
import com.stemlaur.anki.domain.study.SessionIdFactory;
import com.stemlaur.anki.domain.study.spi.fake.InMemoryCardProgresses;
import com.stemlaur.anki.domain.study.spi.fake.InMemorySessions;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.web.RunnerData;

import java.io.IOException;
import java.time.Clock;
import java.util.function.BiConsumer;

class Demo implements BiConsumer<TextIO, RunnerData> {
    private final DeckService deckService;
    private final DeckStudyService deckStudyService;

    private Demo(final DeckService deckService, final DeckStudyService deckStudyService) throws IOException {
        this.deckService = deckService;
        this.deckStudyService = deckStudyService;
        initData(deckService);
    }

    private static void initData(final DeckService deckService) throws IOException {
        importCSV(deckService, "Chinese", "chinese.csv");
        importCSV(deckService, "French", "french.csv");
        importCSV(deckService, "European capitals", "geography.csv");
        importCSV(deckService, "Periodic Table of Elements", "chemistry.csv");
    }

    private static void importCSV(final DeckService deckService, final String title, final String csvFilePath) throws IOException {
        final String deckId = new ImportDeck(deckService, title, csvFilePath)
                .ofResource();
        System.out.println("'" + title + "' deck created with id = " + deckId);
    }

    public static void main(String[] args) throws IOException {
        TextIO textIO = TextIoFactory.getTextIO();
        textIO.getTextTerminal().getProperties().setPromptColor("white");

        final DeckService deckService = new DeckService(new InMemoryDecks(), new SimpleDeckIdFactory(), new FakeDomainEvents());
        DeckStudyService deckStudyService = new DeckStudyService(deckService, new CardProgressService(new InMemoryCardProgresses()), new SessionIdFactory(), new InMemorySessions(), Clock.systemUTC());

        new Demo(deckService, deckStudyService)
                .accept(textIO, null);
    }

    @Override
    public void accept(final TextIO textIO, final RunnerData runnerData) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println("################");
        terminal.println("#    ANKI      #");
        terminal.println("################");
        terminal.println();

        new MainMenu(deckService, deckStudyService, textIO).display();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " - ANKI Program.)";
    }
}
