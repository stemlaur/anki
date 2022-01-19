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
package com.stemlaur.anki.domain.study;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class CardProgressUpdateProgressShould {
    private static final String CARD_ID = "d8394206-2f05-4177-b6c5-495dcc94fbd3";
    private static final Score SCORE_OF_1 = new Score(1);
    private static final LocalDateTime NOW = LocalDateTime.now();

    @Test
    public void updateLastEvaluationAt() {
        final CardProgress cardProgress = CardProgress.init(CARD_ID);
        cardProgress.updateProgress(Opinion.GREEN, NOW);
        assertThat(cardProgress.lastEvaluationAt()).isNotNull();
    }

    @Test
    public void multiplyScoreBy10_when_opinionIsGreen() {
        final Score scoreBefore = new Score(1);

        final CardProgress cardProgress = new CardProgress(CARD_ID, NOW, scoreBefore)
                .updateProgress(Opinion.GREEN, NOW);

        final Score actual = cardProgress.score();
        assertThat(actual).isEqualTo(new Score(10));
    }

    @Test
    public void divideScoreBy2_when_opinionIsOrange() {
        final Score scoreBefore = new Score(10);

        final CardProgress cardProgress = new CardProgress(CARD_ID, NOW, scoreBefore);
        cardProgress.updateProgress(Opinion.ORANGE, NOW);

        final Score actual = cardProgress.score();
        assertThat(actual).isEqualTo(new Score(5));
    }

    @Test
    public void divideScoreBy5_when_opinionIsRed() {
        final Score scoreBefore = new Score(10);

        final CardProgress cardProgress = new CardProgress(CARD_ID, NOW, scoreBefore);
        cardProgress.updateProgress(Opinion.RED, NOW);

        final Score actual = cardProgress.score();
        assertThat(actual).isEqualTo(new Score(2));
    }

    @Test
    public void neverSetScoreUnder1() {
        final CardProgress cardProgressRed = new CardProgress(CARD_ID, NOW, SCORE_OF_1)
                .updateProgress(Opinion.RED, NOW);
        assertThat(cardProgressRed.score()).isEqualTo(SCORE_OF_1);

        final CardProgress cardProgressOrange = new CardProgress(CARD_ID, NOW, SCORE_OF_1)
                .updateProgress(Opinion.ORANGE, NOW);
        assertThat(cardProgressOrange.score()).isEqualTo(SCORE_OF_1);
    }
}
