package com.stemlaur.livingdocumentation.plugin.generator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString
@Getter
@AllArgsConstructor
public class LivingDocumentation {
    private final List<Layer> layers;

    public LivingDocumentation() {
        this.layers = new ArrayList<>();
    }

    public void addLayer(Layer layer) {
        this.layers.add(layer);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final LivingDocumentation that = (LivingDocumentation) o;
        return Objects.equals(layers, that.layers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(layers);
    }

    public enum Type {
        AGGREGATE, ENTITY, DOMAIN_EVENT, VALUE_OBJECT, DOMAIN_SERVICE, REPOSITORY, NOT_FOUND
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static class Layer {
        private final String title;
        private final String comment;
        private final String link;

        private final List<Definition> definitions;

        public Layer(final String title,
                     final String comment,
                     final String link) {
            this(title, comment, link, new ArrayList<>());
        }

        public void addDefinition(final Definition definition) {
            this.definitions.add(definition);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Layer layer = (Layer) o;
            return Objects.equals(title, layer.title) && Objects.equals(comment, layer.comment) && Objects.equals(link, layer.link) && Objects.equals(definitions, layer.definitions);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, comment, link, definitions);
        }
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static class Definition {
        private final String title;
        private final String comment;
        private final List<Type> types;

        public String getTitle() {
            return title;
        }

        public String getComment() {
            return comment;
        }

        public List<Type> getTypes() {
            return types;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Definition that = (Definition) o;
            return Objects.equals(title, that.title) && Objects.equals(comment, that.comment) && Objects.equals(types, that.types);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, comment, types);
        }
    }
}

