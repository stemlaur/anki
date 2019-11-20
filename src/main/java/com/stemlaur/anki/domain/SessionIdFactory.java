package com.stemlaur.anki.domain;

import java.util.UUID;

public class SessionIdFactory {
    public String create() {
        return UUID.randomUUID().toString();
    }
}
