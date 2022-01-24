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
package com.stemlaur.anki.demo.menus;

import com.stemlaur.anki.domain.catalog.CardDetail;
import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.catalog.api.FindDecks.DeckSnapshot;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Optional;

import static java.lang.String.format;

public final class CatalogMenu {
    private final DeckService deckService;
    private final TextIO textIO;

    public CatalogMenu(final DeckService deckService, final TextIO textIO) {
        this.deckService = deckService;
        this.textIO = textIO;
    }

    public void display() {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        String givenDeckId;
        while (true) {
            final CatalogMenuItems n = textIO.newEnumInputReader(CatalogMenuItems.class)
                    .withValueFormatter(item -> item.title).read("");
            switch (n) {
                case CREATE_A_DECK:
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
                case ADD_A_CARD:
                    terminal.println("========== You want to add a card to a deck ==========");
                    givenDeckId = textIO.newStringInputReader().withMinLength(36).withMaxLength(36).read("Id of the deck:");
                    final String givenQuestion = textIO.newStringInputReader().withMinLength(3).read("Card question:");
                    final String givenAnswer = textIO.newStringInputReader().withMinLength(1).read("Card answer:");
                    deckService.addCard(givenDeckId, new CardDetail(givenQuestion, givenAnswer));
                    terminal.println("========== The card has been added successfuly ==========");
                    break;
                case VIEW_A_DECK:
                    terminal.println("========== You want to view a deck ==========");
                    givenDeckId = textIO.newStringInputReader().withMinLength(36).withMaxLength(36).read("Id of the deck:");
                    final Optional<DeckSnapshot> optionalDeck = deckService.byId(givenDeckId);
                    if (optionalDeck.isEmpty()) {
                        terminal.println("========== The deck not exist ==========");
                    } else {
                        final DeckSnapshot deckToView = optionalDeck.orElseThrow();
                        terminal.println("Deck with title '" + deckToView.getTitle() + "'");
                        deckToView.getCards().forEach(card -> terminal.println("  >  " + card.getQuestion() + " -> " + card.getAnswer()));
                        terminal.println("========================================");
                    }
                    break;
                case LIST_ALL_DECKS:
                    terminal.println("========== You want to list all decks ==========");
                    this.deckService.all()
                            .forEach(deck -> terminal.println("Deck with title '" + deck.getTitle() + "' and id " + deck.getId()));
                case EXIT:
                    return;
                default:
                    throw new IllegalStateException("Unexpected value: " + n);
            }
            terminal.println();
        }
    }

    public enum CatalogMenuItems {
        CREATE_A_DECK("Create a deck"),
        ADD_A_CARD("Add a card to a deck"),
        VIEW_A_DECK("View a deck"),
        LIST_ALL_DECKS("List all decks"),
        EXIT("Go back to main menu");
        private final String title;

        CatalogMenuItems(final String title) {
            this.title = title;
        }
    }
}
