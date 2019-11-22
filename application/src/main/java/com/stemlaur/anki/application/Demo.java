/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stemlaur.anki.application;

import com.stemlaur.anki.application.infrastructure.InMemoryDeckRepository;
import com.stemlaur.anki.domain.catalog.CardDetail;
import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckService;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.web.RunnerData;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

import static java.lang.String.format;

public class Demo implements BiConsumer<TextIO, RunnerData> {
    private final DeckService deckService;

    private Demo(final DeckService deckService) {
        this.deckService = deckService;
        initData(deckService);
    }

    private static void initData(final DeckService deckService) {
        final String deckId = deckService.create("Brain and its mysteries");
        System.out.println("Deck id = " + deckId);
        deckService.addCard(deckId,
                new CardDetail("What lobe is involved in processing sensory input ?",
                        "The temporal lobe."));
        deckService.addCard(deckId,
                new CardDetail("What is the lunate sulcus ?",
                        "A fissure in the occipital lobe."));
        deckService.addCard(deckId,
                new CardDetail("What part of the brain is primarily involved in visual perception ?",
                        "Occipital lobe."));
        deckService.addCard(deckId,
                new CardDetail("What is the name of the standard used to order the human brain anatomical regions ?",
                        "The neuroanatomy hierarchies."));
        deckService.addCard(deckId,
                new CardDetail( "Which lobe is the largest of the four major lobes of the brain in mammals ?",
                        "The frontal lobe."));
        deckService.addCard(deckId,
                new CardDetail( "Which lobe is positioned above the temporal lobe and behind the frontal lobe ?",
                        "The parietal lobe."));
    }

    public static void main(String[] args) {
        TextIO textIO = TextIoFactory.getTextIO();
        new Demo(new DeckService(new InMemoryDeckRepository()))
                .accept(textIO, null);
    }

    @Override
    public void accept(TextIO textIO, RunnerData runnerData) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println("################");
        terminal.println("#    ANKI      #");
        terminal.println("################");
        terminal.println();

        String givenDeckId;
        while (true) {
            displayMenu(terminal);
            final Integer n = textIO.newIntInputReader().withNumberedPossibleValues(List.of(1, 2, 3, 4)).read("");
            switch (n) {
                case 1:
                    terminal.println("========== You want to create a deck ==========");
                    final String deckTitle = textIO.newStringInputReader().withMinLength(3).read("Title for the deck:");
                    final String deckId = deckService.create(deckTitle);
                    terminal.println(format("The id of the deck is %s", deckId));
                    boolean copyToClipboard = textIO.newBooleanInputReader().withDefaultValue(false).read("Copy id to clipboard ?");
                    if (copyToClipboard) {
                        StringSelection stringSelection = new StringSelection(deckId);
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(stringSelection, null);
                        terminal.println("Deck id copied!");
                    }
                    break;
                case 2:
                    terminal.println("========== You want to add a card to a deck ==========");
                    givenDeckId = textIO.newStringInputReader().withMinLength(36).withMaxLength(36).read("Id of the deck:");
                    final String givenQuestion = textIO.newStringInputReader().withMinLength(3).read("Card question:");
                    final String givenAnswer = textIO.newStringInputReader().withMinLength(1).read("Card answer:");
                    deckService.addCard(givenDeckId, new CardDetail(givenQuestion, givenAnswer));
                    terminal.println("========== The card has been added successfuly ==========");
                    break;
                case 3:
                    terminal.println("========== You want to view a deck ==========");
                    givenDeckId = textIO.newStringInputReader().withMinLength(36).withMaxLength(36).read("Id of the deck:");
                    final Optional<Deck> optionalDeck = deckService.findDeckById(givenDeckId);
                    if (optionalDeck.isEmpty()) {
                        terminal.println("========== The deck not exist ==========");
                    } else {
                        final Deck deckToView = optionalDeck.orElseThrow();
                        terminal.println("Deck with title '" + deckToView.title() + "'");
                        deckToView.cards().forEach(card -> terminal.println("  >  " + card.detail().question() + " -> " + card.detail().answer()));
                        terminal.println("========================================");
                    }
                    break;
                case 4:
                    terminal.println("Goodbye!");
                    textIO.dispose();
                    System.exit(0);
            }

            terminal.println();
            terminal.println();
            terminal.println();
        }
    }

    private static void displayMenu(TextTerminal<?> terminal) {
        terminal.println("#####################################");
        terminal.println("# 1 - to create a deck");
        terminal.println("# 2 - to add a card to a deck");
        terminal.println("# 3 - to view a deck");
        terminal.println("# 4 - to exit");
        terminal.println("#####################################");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " - ANKI Program.)";
    }
}