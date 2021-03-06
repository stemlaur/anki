package com.stemlaur.anki.demo.menus;

import com.stemlaur.anki.domain.study.CardToStudy;
import com.stemlaur.anki.domain.study.DeckStudyService;
import com.stemlaur.anki.domain.study.Opinion;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;

final class StudyMenu {
    private final DeckStudyService deckStudyService;
    private final TextIO textIO;

    public enum StudyMenuItems {
        CREATE_A_SESSION("Create a session"),
        NEXT_CARD_TO_STUDY("Study next card"),
        STOP_SESSION("Stop session");
        private final String title;

        StudyMenuItems(final String title) {
            this.title = title;
        }
    }

    StudyMenu(final DeckStudyService deckStudyService,
              final TextIO textIO) {
        this.deckStudyService = deckStudyService;
        this.textIO = textIO;
    }

    void display() {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        String currentSessionId = null;
        while (true) {
            final StudyMenuItems n = textIO.newEnumInputReader(StudyMenuItems.class)
                    .withValueFormatter(item -> item.title).read("");
            switch (n) {
                case CREATE_A_SESSION:
                    terminal.println("========== You want to create session ==========");
                    String givenDeckId = textIO.newStringInputReader().withMinLength(36).withMaxLength(36).read("Id of the deck:");
                    currentSessionId = this.deckStudyService.startStudySession(givenDeckId);
                    break;
                case NEXT_CARD_TO_STUDY:
                    terminal.println("========== You want to next card to study ==========");
                    final CardToStudy cardToStudy = this.deckStudyService.nextCardToStudy(currentSessionId).orElseThrow();
                    terminal.println("==================================================");
                    terminal.println(cardToStudy.question());
                    terminal.println("Answer in 5 seconds");
                    wait5Seconds();
                    terminal.println(cardToStudy.answer());
                    terminal.println("==================================================");
                    final Opinion opinion = textIO.newEnumInputReader(Opinion.class)
                            .withValueFormatter(Enum::name).read("");
                    this.deckStudyService.study(currentSessionId, cardToStudy.id(), opinion);
                    break;
                case STOP_SESSION:
                    return;
            }
            terminal.println();
        }
    }

    private static void wait5Seconds() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
