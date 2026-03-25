Feature: Train Search Functionality (redRail)

  Scenario: User searches trains from Howrah to New Delhi
    Given User is on redRail homepage
    When User clicks on Train Tickets
    And User enters "How" in From field
  And User selects "Howrah" from suggestions
  And User enters "New Delhi" in To field
  And User selects "New Delhi" from suggestions
    And User clicks on Search Trains button
    Then Train results should be displayed
