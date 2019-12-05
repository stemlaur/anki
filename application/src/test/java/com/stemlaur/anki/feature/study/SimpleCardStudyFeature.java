package com.stemlaur.anki.feature.study;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/com/stemlaur/anki/feature/study",
        plugin = {"pretty", "html:target/cucumber/ankifeatures"},
        extraGlue = "com.stemlaur.anki.feature.common")
public class SimpleCardStudyFeature {
}
