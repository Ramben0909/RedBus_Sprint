Feature: Invalid Destination Search (redRail)

  Scenario: User searches trains with an invalid destination
    Given User is on redRail homepage
    When User clicks on Train Tickets
    And User enters "How" in From field
    And User selects "Howrah" from suggestions
    And User enters "XYZINVALID" in To field
    Then No suggestions should be displayed for invalid destination