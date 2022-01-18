package com.stemlaur.anki.domain.study;

import com.stemlaur.livingdocumentation.annotation.ValueObject;

/**
 * This value object represents the opinion the player has about the knowledge of a card.
 */
@ValueObject
public enum Opinion {
    ORANGE, RED, GREEN
}
