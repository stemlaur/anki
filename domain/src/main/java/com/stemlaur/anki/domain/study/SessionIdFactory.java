package com.stemlaur.anki.domain.study;

import java.util.UUID;

public class SessionIdFactory {
    public String create() {
        return UUID.randomUUID().toString();
    }
}
