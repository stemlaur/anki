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
package com.stemlaur.anki.domain;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.type;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "com.stemlaur.anki.domain", importOptions = ImportOption.DoNotIncludeTests.class)
public class DomainDependenciesShould {

    @ArchTest
    public static final ArchRule not_be_technical = classes()
            .that().resideInAPackage("com.stemlaur.anki.domain..")
            .should().onlyDependOnClassesThat(resideInAnyPackage(
                    "java..",
                    "org.apache.commons..",
                    "com.stemlaur.livingdocumentation..",
                    "com.stemlaur.anki.domain.."
            ).or(type(int[].class))) // See https://github.com/TNG/ArchUnit/issues/570
            .as("The domain should be independent of infrastructure")
            .because("business rules evolve at a different rhythm");

}
