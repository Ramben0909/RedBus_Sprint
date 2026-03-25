Feature: Bus Search Functionality on RedBus Homepage

  Background:
    Given the user is on the RedBus homepage

  @both_empty
  Scenario: Both source and destination empty
    When the user clicks the search buses button
    Then the error message "Please enter source and destination" is displayed

  @only_source
  Scenario: Only source filled
    When the user fills source as "Mumbai" and selects the suggestion
    And the user clicks the search buses button
    Then the error message containing "Please enter source and destination" is displayed

  @only_destination
  Scenario: Only destination filled
    When the user fills destination as "Pune" and selects the suggestion
    And the user clicks the search buses button
    Then the error message containing "Please enter source and destination" is displayed

  @both_filled_search
  Scenario: Both fields filled + search
    When the user fills source as "Mumbai" and selects the suggestion
    And the user fills destination as "Pune" and selects the suggestion
    And the user clicks the search buses button
    Then the user is navigated to the search results page

  @both_filled_with_date
  Scenario: Both fields + date selection
    When the user fills source as "Mumbai" and selects the suggestion
    And the user fills destination as "Pune" and selects the suggestion
    And the user clicks the date field
    And the user selects date "27"
    And the user clicks the search buses button
    Then the user is navigated to the search results page

  @date_second_click
  Scenario: Date field second click (re-open calendar)
    When the user fills source as "Mumbai" and selects the suggestion
    And the user fills destination as "Pune" and selects the suggestion
    And the user clicks the date field
    And the user selects date "27"
    And the user clicks the date field again
    And the user selects date "28"
    Then the calendar reopens and date "28" is selected successfully

  @remove_source
  Scenario: Remove source after selection
    When the user fills source as "Mumbai" and selects the suggestion
    And the user fills destination as "Pune" and selects the suggestion
    And the user removes the source
    Then the source field is cleared