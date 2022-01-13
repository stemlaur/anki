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
@AnalyzeClasses(packages = "fr.edf.nexusone", importOptions = {ImportOption.DoNotIncludeTests.class})
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
