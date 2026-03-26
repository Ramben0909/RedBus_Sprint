Feature: Clear Filter Validation (redRail)

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