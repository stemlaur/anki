package com.stemlaur.anki.demo.menus;

import com.stemlaur.anki.domain.catalog.CardDetail;
import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckService;
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
                case LIST_ALL_DECKS:
                    terminal.println("========== You want to list all decks ==========");
                    this.deckService.findAll()
                            .forEach(deck -> terminal.println("Deck with title '" + deck.title() + "' and id " + deck.id()));
                case EXIT:
                    return;
            }
            terminal.println();
        }
    }
}
