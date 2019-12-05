package com.stemlaur.anki.rest.controllers;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.and;
import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation;

@EnableSwagger2
@Import({
        BeanValidatorPluginsConfiguration.class
})
@Configuration
class LivingDocumentationConfiguration {

    @Bean
    Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(documentedControllers())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    private Predicate<RequestHandler> documentedControllers() {
        return and(
                controllers(),
                inSubPackageOf(LivingDocumentationConfiguration.class)
        );
    }

    private Predicate<RequestHandler> inSubPackageOf(Class clazz) {
        return basePackage(clazz.getPackage().getName());
    }

    private Predicate<RequestHandler> controllers() {
        return or(
                withClassAnnotation(Controller.class),
                withClassAnnotation(RestController.class)
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ANKI REST API")
                .description("Provides ANKI REST API documentation.")
                .version("v1")
                .build();
    }

}
