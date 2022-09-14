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
package com.stemlaur.anki.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@Tag("Architecture")
@AnalyzeClasses(packages = "com.stemlaur.anki", importOptions = {ImportOption.DoNotIncludeTests.class})
public class ResourceApiOperationDocumentationArchitectureTest {

    /**
     * This test forces all the mapping methods to be annotated with @ApiOperation.
     */
    @ArchTest
    static final ArchRule resourcesMethodShouldBeAnnotatedWithApiOperation =
            methods()
                    .that()
                    .areDeclaredInClassesThat()
                    .areAnnotatedWith(RestController.class)
                    .and()
                    .areAnnotatedWith(GetMapping.class)
                    .or().areAnnotatedWith(PostMapping.class)
                    .or().areAnnotatedWith(PutMapping.class)
                    .or().areAnnotatedWith(DeleteMapping.class)
                    .should()
                    .beAnnotatedWith(ApiOperation.class);
}
