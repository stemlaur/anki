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
package com.stemlaur.anki.application.controllers.livingdoc.generator;

import com.stemlaur.livingdocumentation.annotation.DomainEvent;
import com.stemlaur.livingdocumentation.annotation.DomainLayer;
import com.stemlaur.livingdocumentation.annotation.DomainService;
import com.stemlaur.livingdocumentation.annotation.Entity;
import com.stemlaur.livingdocumentation.annotation.ValueObject;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaPackage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
public class ParseLivingDocumentation {
    private static final String LIVING_DOC_ANNOTATION_PACKAGE_NAME = DomainLayer.class.getPackage().getName();
    private static final Set<String> LAYER_ANNOTATIONS = new HashSet<>();

    static {
        LAYER_ANNOTATIONS.add(DomainLayer.class.getCanonicalName());
    }

    public ParseLivingDocumentation() {
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
        final Map<JavaPackage, JavaAnnotation> contexts = new TreeMap<>(Comparator.comparing(JavaPackage::getName));

        for (JavaPackage p : builder.getClasses().stream().map(JavaClass::getPackage).distinct().toList()) {
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
        List<JavaClass> sortedClasses = getAllClassesAndSubClasses(pckge).stream()
                .sorted(Comparator.comparing(JavaClass::getName))
                .collect(Collectors.toList());
        for (JavaClass clss : sortedClasses) {
            if (isBusinessMeaningful(clss)) {
                final LivingDocumentation.Definition definition = parseDefinition(clss);
                layer.addDefinition(definition);
            }
        }
        return layer;
    }

    private Collection<JavaClass> getAllClassesAndSubClasses(final JavaPackage pckge) {
        final Collection<JavaClass> allClasses = new ArrayList<>(pckge.getClasses());
        for (JavaPackage subPackage : pckge.getSubPackages()) {
            allClasses.addAll(subPackage.getClasses());
        }
        return allClasses;
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
