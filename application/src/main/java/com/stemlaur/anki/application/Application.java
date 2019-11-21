package com.stemlaur.anki.application;

import com.stemlaur.anki.application.infrastructure.InMemoryDeckRepository;
import com.stemlaur.anki.domain.catalog.DeckService;

import java.io.PrintStream;
import java.util.Scanner;

import static java.lang.String.format;

public final class Application {
    private static final Scanner STDIN = new Scanner(System.in);
    private static final PrintStream STDOUT = System.out;

    public static void main(String[] args) {
        final DeckService deckService = new DeckService(new InMemoryDeckRepository());

        STDOUT.println("Welcome to ANKI:");
        while (true) {
            try {
                displayMenu();
                final String n = STDIN.next();
                switch (n) {
                    case "1":
                        STDOUT.println("========== You want to create a deck ==========");
                        final String deckTitle = askUserForTheDeckTitle();
                        final String deckId = deckService.create(deckTitle);
                        STDOUT.println(format("The id of the deck is %s", deckId));
                        break;
                    case "2":
                        STDOUT.println("Goodbye!");
                        System.exit(0);
                    default:
                        STDOUT.println(format("I did not understand %s please try again", n));
                }
            } catch (Exception e) {
                System.err.println("An exception occured : " + e.getMessage());
            }
        }
    }

    private static String askUserForTheDeckTitle() {
        System.out.println("Please provide me title of the deck:");
        return STDIN.next();
    }

    private static void displayMenu() {
        System.out.println("");
        System.out.println("1 - to create a deck");
        System.out.println("2 - to exit");
    }
}
