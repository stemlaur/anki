package com.stemlaur.anki.demo.menus;

import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.study.DeckStudyService;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

public final class MainMenu {
    private final DeckService deckService;
    private final DeckStudyService deckStudyService;
    private final TextIO textIO;

    public enum MainMenuItems {
        CATALOG("Deck management (Catalog)"),
        STUDY("Study decks"),
        EXIT("Exit");
        private final String title;

        MainMenuItems(final String title) {
            this.title = title;
        }
    }

    public MainMenu(final DeckService deckService,
                    final DeckStudyService deckStudyService,
                    final TextIO textIO) {
        this.deckService = deckService;
        this.deckStudyService = deckStudyService;
        this.textIO = textIO;
    }

    public void display() {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        while (true) {
            final MainMenuItems n = textIO.newEnumInputReader(MainMenuItems.class)
                    .withValueFormatter(item -> item.title).read("");
            switch (n) {
                case CATALOG:
                    terminal.println("========== You choosed CATALOG ==========");
                    new CatalogMenu(this.deckService, this.textIO).display();
                    break;
                case STUDY:
                    terminal.println("========== You choosed STUDY ==========");
                    new StudyMenu(this.deckStudyService, this.textIO).display();
                    break;
                case EXIT:
                    terminal.println("Goodbye!");
                    textIO.dispose();
                    System.exit(0);
            }

            terminal.println();
        }
    }

    private static void printMenu(TextTerminal<?> terminal) {
        terminal.println("#####################################");
        terminal.println("# 1 - to create a deck");
        terminal.println("# 2 - to add a card to a deck");
        terminal.println("# 3 - to view a deck");
        terminal.println("# 4 - to exit");
        terminal.println("#####################################");
    }
}
