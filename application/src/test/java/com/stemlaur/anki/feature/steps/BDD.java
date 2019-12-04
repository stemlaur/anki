package com.stemlaur.anki.feature.steps;

public final class BDD<T> {

    public static <T> T given(T subject) {
        return subject;
    }

    public static <T> T and(T subject) {
        return subject;
    }


    public static <T> T when(T subject) {
        return subject;
    }
}
