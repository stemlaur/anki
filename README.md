# Anki [![Build Status](https://travis-ci.org/stemlaur/anki.svg?branch=master)](https://travis-ci.org/stemlaur/anki)
## Description
Anki is a flashcard program that utilizes spaced repetition.
A student can study a deck, therefore cards will be shown with questions.
The student then gives its self-evaluation about its knowledge in the form 
of colors (GREEN, ORANGE and RED).
The card will be shown again in the future depending on the last self-evaluation.


## Usage
For the moment, the service does not contain any executable. 

You can run the tests using:

```bash
> mvn clean test
```

This usage paragraph will of course change as the project evolves.

 ## Contribute
 - keep the domain free of any infrastructure related details
    - in order to be able to log and/or publish metrics [domain probe](https://martinfowler.com/articles/domain-oriented-observability.html)
     should be used
 - the `master` branch is protected, any pull request can be proposed, they will be merge if the CI is green and after a review
 - please update the READMEs as soon as the usage changes
 - if you have any comment or issue, please file either a pull request or you can create an issue
