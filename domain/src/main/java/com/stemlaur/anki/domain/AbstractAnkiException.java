package com.stemlaur.anki.domain;

public class AbstractAnkiException extends RuntimeException {
    protected AbstractAnkiException(String message, Object... parameters) {
        super(String.format(message, parameters));
    }
}
