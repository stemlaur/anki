package com.stemlaur.livingdocumentation.plugin.generator;

import com.stemlaur.livingdocumentation.annotation.DomainEvent;
import com.stemlaur.livingdocumentation.annotation.DomainLayer;
import com.stemlaur.livingdocumentation.annotation.DomainService;
import com.stemlaur.livingdocumentation.annotation.Entity;
import com.stemlaur.livingdocumentation.annotation.Repository;
import com.stemlaur.livingdocumentation.annotation.ValueObject;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaPackage;
import org.apache.maven.plugin.logging.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ParseLivingDocumentation {
    private static final String LIVING_DOC_ANNOTATION_PACKAGE_NAME = DomainLayer.class.getPackage().getName();
    private static final Set<String> LAYER_ANNOTATIONS = new HashSet<>();

    static {
        LAYER_ANNOTATIONS.add(DomainLayer.class.getCanonicalName());
    }

    private final Log log;

    public ParseLivingDocumentation(Log log) {
        this.log = log;
    }

    public LivingDocumentation parseLivingDocumentation(final JavaProjectBuilder builder) {
        final LivingDocumentation livingDocumentation = new LivingDocumentation();
        final Map<JavaPackage, JavaAnnotation> inventory = layerInventory(builder);
        for (Map.Entry<JavaPackage, JavaAnnotation> entry : inventory.entrySet()) {
            final JavaPackage pckge = entry.getKey();
            final JavaAnnotation bc = entry.getValue();
            LivingDocumentation.Layer layer = parseLayer(pckge, bc);
            livingDocumentation.addLayer(layer);
        }
        return livingDocumentation;
    }

    /**
     * Creates an inventory of every package that defines a Layer
     */
    private Map<JavaPackage, JavaAnnotation> layerInventory(JavaProjectBuilder builder) {
        final Map<JavaPackage, JavaAnnotation> contexts = new HashMap<>();
        for (JavaPackage p : builder.getPackages()) {
            final JavaAnnotation layer = layerAnnotation(p);
            if (layer != null) {
                contexts.put(p, layer);
            }
        }
        log.info("layer Inventory " + contexts);
        return contexts;
    }

    /**
     * Extracts the Layer Annotation of the given package, or null if
     * there's none
     */
    private JavaAnnotation layerAnnotation(JavaPackage pkge) {
        for (JavaAnnotation annotation : pkge.getAnnotations()) {
            final JavaClass type = annotation.getType();
            if (LAYER_ANNOTATIONS.contains(type.getFullyQualifiedName())) {
                return annotation;
            }
        }
        return null;
    }

    private LivingDocumentation.Layer parseLayer(final JavaPackage pckge, final JavaAnnotation bc) {
        final LivingDocumentation.Layer layer = parseLayerDetails(pckge, bc);
        for (JavaClass clss : pckge.getClasses()) {
            if (isBusinessMeaningful(clss)) {
                final LivingDocumentation.Definition definition = parseDefinition(clss);
                layer.addDefinition(definition);
            }
        }
        return layer;
    }

    private boolean isBusinessMeaningful(JavaClass doc) {
        for (JavaClass interfaz : doc.getInterfaces()) {
            if (interfaz.getCanonicalName().startsWith(LIVING_DOC_ANNOTATION_PACKAGE_NAME)) {
                return true;
            }
        }

        for (JavaAnnotation annotation : doc.getAnnotations()) {
            if (annotation.getType().getCanonicalName().startsWith(LIVING_DOC_ANNOTATION_PACKAGE_NAME)) {
                return true;
            }
        }
        return false;
    }

    private LivingDocumentation.Layer parseLayerDetails(JavaPackage pckge, JavaAnnotation bc) {
        final String bcName = (String) bc.getNamedParameter("name");
        final Object link = bc.getNamedParameter("link");
        return new LivingDocumentation.Layer(bcName.trim().replaceAll("\"", ""),
                pckge.getComment(),
                link == null ? "" : link.toString());
    }

    private LivingDocumentation.Definition parseDefinition(JavaClass clss) {
        Objects.requireNonNull(clss.getComment(), "The class " + clss + " is not documented");
        return new LivingDocumentation.Definition(clss.getName(), clss.getComment(), this.convertToType(clss.getAnnotations()));
    }

    private List<LivingDocumentation.Type> convertToType(List<JavaAnnotation> annotations) {
        final List<LivingDocumentation.Type> types = new ArrayList<>();
        for (JavaAnnotation annotation : annotations) {
            final String fullyQualifiedName = annotation.getType().getFullyQualifiedName();
            if (isAnnotation(fullyQualifiedName, DomainEvent.class)) {
                types.add(LivingDocumentation.Type.DOMAIN_EVENT);
            } else if (isAnnotation(fullyQualifiedName, DomainService.class)) {
                types.add(LivingDocumentation.Type.DOMAIN_SERVICE);
            } else if (isAnnotation(fullyQualifiedName, Entity.class)) {
                types.add(LivingDocumentation.Type.ENTITY);
            } else if (isAnnotation(fullyQualifiedName, Repository.class)) {
                types.add(LivingDocumentation.Type.REPOSITORY);
            } else if (isAnnotation(fullyQualifiedName, ValueObject.class)) {
                types.add(LivingDocumentation.Type.VALUE_OBJECT);
            }
        }
        return types;
    }

    private boolean isAnnotation(String className, Class<?> clazz) {
        return className.equalsIgnoreCase(clazz.getCanonicalName());
    }
}
