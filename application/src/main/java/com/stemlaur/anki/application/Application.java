package com.stemlaur.anki.application;

import com.stemlaur.anki.application.infrastructure.InMemoryDeckRepository;
import com.stemlaur.anki.domain.catalog.CardDetail;
import com.stemlaur.anki.domain.catalog.Deck;
import com.stemlaur.anki.domain.catalog.DeckService;

import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

import static java.lang.String.format;

public final class Application {
    private static final Scanner STDIN = new Scanner(System.in);
    private static final PrintStream STDOUT = System.out;
    private static final PrintStream STDERR = System.err;

    public static void main(String[] args) {
        final DeckService deckService = new DeckService(new InMemoryDeckRepository());

        String givenDeckId;
        STDOUT.println("Welcome to ANKI:");
        while (true) {
            try {
                displayMenu();
                final String n = STDIN.nextLine();
                switch (n) {
                    case "1":
                        STDOUT.println("========== You want to create a deck ==========");
                        final String deckTitle = askUserForTheDeckTitle();
                        final String deckId = deckService.create(deckTitle);
                        STDOUT.println(format("The id of the deck is %s", deckId));
                        break;
                    case "2":
                        STDOUT.println("========== You want to add a card to a deck ==========");
                        givenDeckId = askUserForTheDeckId();
                        final String givenQuestion = askUserForCardQuestion();
                        final String givenAnswer = askUserForCardAnswer();
                        deckService.addCard(givenDeckId, new CardDetail(givenQuestion, givenAnswer));
                        STDOUT.println("========== The card has been added successfuly ==========");
                        break;
                    case "3":
                        STDOUT.println("========== You want to add a card to a deck ==========");
                        givenDeckId = askUserForTheDeckId();
                        final Optional<Deck> optionalDeck = deckService.findDeckById(givenDeckId);
                        if (optionalDeck.isEmpty()) {
                            STDOUT.println("========== The deck not exist ==========");
                        } else {
                            STDOUT.println(optionalDeck.orElseThrow());
                        }
                        break;
                    case "4":
                        STDOUT.println("Goodbye!");
                        System.exit(0);
                    default:
                        STDOUT.println(format("I did not understand %s please try again", n));
                }
            } catch (final Exception e) {
                e.printStackTrace(STDERR);
            }
        }
    }

    private static String askUserForCardAnswer() {
        System.out.println("> Please provide me an answer for the card:");
        return STDIN.nextLine();
    }

    private static String askUserForCardQuestion() {
        System.out.println("> Please provide me a question for the card:");
        return STDIN.nextLine();
    }

    private static String askUserForTheDeckId() {
        System.out.println("> Please provide me the deck id:");
        return STDIN.nextLine();
    }

    private static String askUserForTheDeckTitle() {
        System.out.println("> Please provide me a title for the deck:");
        return STDIN.nextLine();
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("1 - to create a deck");
        System.out.println("2 - to add a card to a deck");
        System.out.println("3 - to view a deck");
        System.out.println("4 - to exit");
    }
}
