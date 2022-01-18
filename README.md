# Anki [![Actions Status](https://github.com/stemlaur/anki/workflows/Build/badge.svg)](https://github.com/stemlaur/anki/actions)
## Description
Anki is a flashcard program that utilizes spaced repetition.
A student can study a deck, therefore cards will be shown with questions.
The student then gives its self-evaluation about its knowledge in the form 
of colors (GREEN, ORANGE and RED).
The card will be shown again in the future depending on the last self-evaluation.

## Usage
To use the service, please run the following commands:

```bash
> mvn clean package
> java -jar demo/target/demo-1.0-SNAPSHOT-jar-with-dependencies.jar
```

This usage paragraph will of course change as the project evolves.

You can check the Swagger documentation at http://localhost:8080/swagger-ui/

You can as well look at the glossary [here](https://htmlpreview.github.io/?https://github.com/stemlaur/anki/blob/master/doc/glossary/index.html) 

## The modules
The modules are splitted into :

 - a module [domain](./domain/README.md) 
 - a module [application](./application/README.md)
 - a module [demo](./demo/README.md)

 ## Contribute
 - keep the domain free of any infrastructure related details
    - in order to be able to log and/or publish metrics [domain probe](https://martinfowler.com/articles/domain-oriented-observability.html)
     should be used
 - the `master` branch is protected, any pull request can be proposed, they will be merge if the CI is green and after a review
 - please update the READMEs as soon as the usage changes
 - if you have any comment or issue, please file either a pull request or you can create an issue

Please read our architecture decisions [here](./doc/architecture).
