Feature: Train Results Functionality (redRail)

  Scenario: Verify AC filtration for available train
    Given User is on redRail homepage
    When User clicks on Train Tickets
    And User enters "How" in From field
    And User selects "Howrah" from suggestions
    And User enters "New Delhi" in To field
    And User selects "New Delhi" from suggestions
    And User opens Date of Journey calendar
    And User selects today's date
    And User clicks on Search Trains button
    And User selects AC Filter
    Then Only AC trains are visible should be displayed

  Scenario: Verify clearing AC filter shows non-AC trains
    Given User is on redRail homepage
    When User clicks on Train Tickets
    And User enters "How" in From field
    And User selects "Howrah" from suggestions
    And User enters "New Delhi" in To field
    And User selects "New Delhi" from suggestions
    And User opens Date of Journey calendar
    And User selects today's date
    And User clicks on Search Trains button
    And User selects AC Filter
    And User clears all filters
    Then Non-AC trains should be visible

  Scenario: Verify free cancellation for available train
    Given User is on redRail homepage
    When User clicks on Train Tickets
    And User enters "How" in From field
    And User selects "Howrah" from suggestions
    And User enters "New Delhi" in To field
    And User selects "New Delhi" from suggestions
    And User opens Date of Journey calendar
    And User selects today's date
    And User clicks on Search Trains button
    And User selects first available train
    Then Free cancellation should be displayed

  Scenario: Verify sort by arrival time for available train
    Given User is on redRail homepage
    When User clicks on Train Tickets
    And User enters "How" in From field
    And User selects "Howrah" from suggestions
    And User enters "New Delhi" in To field
    And User selects "New Delhi" from suggestions
    And User opens Date of Journey calendar
    And User selects today's date
    And User clicks on Search Trains button
    And User sorts by Arrival Time
    Then Trains are sorted in ascendeing order of arrival time