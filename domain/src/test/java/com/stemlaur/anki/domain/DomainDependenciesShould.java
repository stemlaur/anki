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

import static com.stemlaur.anki.domain.DomainDependenciesShould.DOMAIN_PKG;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.type;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static java.lang.String.format;

@AnalyzeClasses(packages = DOMAIN_PKG, importOptions = ImportOption.DoNotIncludeTests.class)
public class DomainDependenciesShould {

    static final String DOMAIN_PKG = "com.stemlaur.anki.domain";

    @ArchTest
    public static final ArchRule enforce_catalog_depends_on_apis_only = not_be_technical_and_only_depend_upon_other_context_apis("catalog");
    @ArchTest
    public static final ArchRule enforce_study_depends_on_apis_only = not_be_technical_and_only_depend_upon_other_context_apis("study");
    @ArchTest
    public static final ArchRule enforce_stats_depends_on_apis_only = not_be_technical_and_only_depend_upon_other_context_apis("stats");

    private static ArchRule not_be_technical_and_only_depend_upon_other_context_apis(final String catalog) {
        return classes()
                .that()
                .resideInAPackage(DOMAIN_PKG + "." + catalog + "..")
                .should()
                .onlyDependOnClassesThat(resideInAnyPackage(
                        only_apis(catalog)
                ).or(type(int[].class))) // See https://github.com/TNG/ArchUnit/issues/570
                .as("The domain should be independent of infrastructure and other context internal")
                .because("business rules evolve at a different rhythm");
    }

    private static String[] only_apis(final String context) {
        return new String[]{
                "java..",
                "org.apache.commons..",
                "com.stemlaur.livingdocumentation..",
                DOMAIN_PKG + ".common..",
                format(DOMAIN_PKG + ".%s..", context),
                DOMAIN_PKG + "..api..",
        };
    }
}
