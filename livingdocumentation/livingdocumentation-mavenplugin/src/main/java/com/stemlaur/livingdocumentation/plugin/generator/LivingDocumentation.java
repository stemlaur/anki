package com.stemlaur.livingdocumentation.plugin.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LivingDocumentation {
    private final List<Layer> layers;

    public LivingDocumentation() {
        this.layers = new ArrayList<>();
    }

    public void addLayer(Layer layer) {
        this.layers.add(layer);

    }

    public List<Layer> getLayers() {
        return layers;
    }

    public static class Layer {
        private final String title;
        private final String comment;
        private final String link;

        private final List<Definition> definitions;

        public Layer(final String title,
                     final String comment,
                     final String link) {
            this.title = title;
            this.comment = comment;
            this.link = link;
            this.definitions = new ArrayList<>();
        }

        public String getTitle() {
            return title;
        }

        public String getComment() {
            return comment;
        }

        public String getLink() {
            return link;
        }

        public List<Definition> getDefinitions() {
            return definitions;
        }

        public void addDefinition(final Definition definition) {
            this.definitions.add(definition);
        }
    }

    public static class Definition {
        private final String title;
        private final String comment;
        private final List<Type> types;

        public Definition(final String title,
                          final String comment,
                          final List<Type> types) {
            this.title = title;
            this.comment = comment;
            this.types = types;
        }

        public String getTitle() {
            return title;
        }

        public String getComment() {
            return comment;
        }
    }

    public enum Type {
        AGGREGATE, ENTITY, DOMAIN_EVENT, VALUE_OBJECT, DOMAIN_SERVICE, REPOSITORY, NOT_FOUND;
    }
}

