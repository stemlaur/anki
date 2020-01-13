package com.stemlaur.anki.feature.study;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com/stemlaur/anki/feature/study",
        plugin = {"pretty", "html:target/cucumber/ankifeatures"},
        extraGlue = "com.stemlaur.anki.feature.common")
public class SimpleCardStudyFeature {
}
