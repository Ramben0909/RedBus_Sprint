Feature: Search Results Page Interactions on RedBus

  Background:
    Given the user is on the RedBus homepage
    When the user fills source as "Mumbai" and selects the suggestion
    And the user fills destination as "Pune" and selects the suggestion
    And the user clicks the search buses button
    Then the user is navigated to the search results page

  @filters
  Scenario: Apply AC and SEATER filters
    When the user applies the "AC" filter
    And the user applies the "SEATER" filter
    Then the filters are successfully applied

  @filter_toggle
  Scenario: Toggle AC and SEATER filters on and off
    When the user toggles the "AC" filter
    And the user toggles the "SEATER" filter
    Then the filters are successfully toggled

  @time_dropdown_select
  Scenario: Select Afternoon from time dropdown
    When the user clicks the time dropdown
    And the user selects "Afternoon" time filter
    Then the Afternoon filter is applied successfully

  @time_dropdown_deselect
  Scenario: Deselect Afternoon from time dropdown
    When the user clicks the time dropdown
    And the user selects "Afternoon" time filter
    And the user clicks the "Afternoon" time filter again to deselect
    Then the Afternoon filter is deselected successfully

  @sort_ratings
  Scenario: Toggle Sort by Ratings
    When the user clicks sort by Ratings
    And the user clicks sort by Ratings again
    Then the sort by Ratings is toggled successfully