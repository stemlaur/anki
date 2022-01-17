package com.stemlaur.anki.domain.study;

import org.jmolecules.ddd.annotation.Factory;

import java.util.UUID;

@Factory
public class SessionIdFactory {
    public String create() {
        return UUID.randomUUID().toString();
    }
}
