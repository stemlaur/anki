Feature: Study cards
  I should be able to study cards from deck.

  Scenario: Study a card from a deck
    Given I create a deck with title "Brain and its mysteries"
    And I add a card with question "What part of the brain is primarily involved in visual perception ?" and answer "Occipital lobe" to the deck
    And I start a study session for the deck
    When I ask for the next card to study
    Then The card should have question "What part of the brain is primarily involved in visual perception ?" and answer "Occipital lobe"

  Scenario: Next card depends on the progress
    Given I create a deck with title "Brain and its mysteries"
    And I add a card with question "What part of the brain is primarily involved in visual perception ?" and answer "Occipital lobe" to the deck
    And I add a card with question "What is the lunate sulcus ?" and answer "A fissure in the occipital lobe." to the deck
    And I start a study session for the deck
    And I ask for the next card to study
    And I study the card with opinion "GREEN"
    When I ask for the next card to study
    Then the second card should be different
