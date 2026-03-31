Feature: Bus Search Functionality on RedBus Homepage

  Background:
    Given the user is on the RedBus homepage

  # ── Static error scenarios (no Excel needed) ─────────────────────────────

  @both_empty
  Scenario: Both source and destination empty
    When the user clicks the search buses button
    Then the error message "Please enter source and destination" is displayed

  # ── Excel-driven: only source / only destination ─────────────────────────

  @only_source
  Scenario Outline: Only source filled - <testCaseId>
    When the user searches with data from excel row "<testCaseId>"
    And the user clicks the search buses button
    Then the error message containing "Please enter source and destination" is displayed

    Examples:
      | testCaseId |
      | BS_04      |

  @only_destination
  Scenario Outline: Only destination filled - <testCaseId>
    When the user searches with data from excel row "<testCaseId>"
    And the user clicks the search buses button
    Then the error message containing "Please enter source and destination" is displayed

    Examples:
      | testCaseId |
      | BS_07      |

  # ── Excel-driven: happy-path search ──────────────────────────────────────

  @both_filled_search
  Scenario Outline: Both fields filled and search - <testCaseId>
    When the user searches with data from excel row "<testCaseId>"
    And the user clicks the search buses button
    Then the user is navigated to the search results page

    Examples:
      | testCaseId |
      | BS_02      |

  # ── Excel-driven: both fields + date ─────────────────────────────────────

  @both_filled_with_date
  Scenario Outline: Both fields with date selection - <testCaseId>
    When the user searches with data from excel row "<testCaseId>"
    And the user clicks the search buses button
    Then the user is navigated to the search results page

    Examples:
      | testCaseId |
      | BS_01      |

  # ── Excel-driven: date second click ──────────────────────────────────────

  @date_second_click
  Scenario Outline: Date field second click re-open calendar - <testCaseId>
    When the user searches with data from excel row "<testCaseId>"
    And the user clicks the date field again
    And the user selects date "<testCaseId>"
    Then the calendar reopens and date "<testCaseId>" is selected successfully

    Examples:
      | testCaseId |
      | BS_10      |

  # ── Excel-driven: remove source ───────────────────────────────────────────

  @remove_source
  Scenario Outline: Remove source after selection - <testCaseId>
    When the user searches with data from excel row "<testCaseId>"
    And the user removes the source
    Then the source field is cleared

    Examples:
      | testCaseId |
      | BS_01      |