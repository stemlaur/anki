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
package com.stemlaur.anki.application.demo;

import com.stemlaur.anki.application.demo.menus.MainMenu;
import com.stemlaur.anki.application.infrastructure.InMemoryCardProgressRepository;
import com.stemlaur.anki.application.infrastructure.InMemoryDeckRepository;
import com.stemlaur.anki.application.infrastructure.InMemorySessionRepository;
import com.stemlaur.anki.domain.catalog.CardDetail;
import com.stemlaur.anki.domain.catalog.DeckService;
import com.stemlaur.anki.domain.common.Clock;
import com.stemlaur.anki.domain.study.CardProgressService;
import com.stemlaur.anki.domain.study.DeckStudyService;
import com.stemlaur.anki.domain.study.SessionIdFactory;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.web.RunnerData;

import java.util.function.BiConsumer;

public class Demo implements BiConsumer<TextIO, RunnerData> {
    private final DeckService deckService;
    private final DeckStudyService deckStudyService;

    private Demo(final DeckService deckService, final DeckStudyService deckStudyService) {
        this.deckService = deckService;
        this.deckStudyService = deckStudyService;
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
                new CardDetail("Which lobe is the largest of the four major lobes of the brain in mammals ?",
                        "The frontal lobe."));
        deckService.addCard(deckId,
                new CardDetail("Which lobe is positioned above the temporal lobe and behind the frontal lobe ?",
                        "The parietal lobe."));
    }

    public static void main(String[] args) {
        TextIO textIO = TextIoFactory.getTextIO();

        final DeckService deckService = new DeckService(new InMemoryDeckRepository());
        DeckStudyService deckStudyService = new DeckStudyService(deckService, new CardProgressService(new InMemoryCardProgressRepository()), new SessionIdFactory(), new InMemorySessionRepository(), new Clock());

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