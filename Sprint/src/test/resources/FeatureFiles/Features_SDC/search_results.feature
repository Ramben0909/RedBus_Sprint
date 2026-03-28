Feature: Search Results Page Interactions on RedBus

  Background:
    Given the user is on the RedBus homepage
    When the user fills source as "Mumbai" and selects the suggestion
    And the user fills destination as "Pune" and selects the suggestion
    And the user clicks the search buses button
    Then the user is navigated to the search results page

  # ── Excel-driven: apply filters ───────────────────────────────────────────

  @filters
  Scenario Outline: Apply bus type filters - <testCaseId>
    When the user applies filters from excel row "<testCaseId>"
    Then the filters from excel row "<testCaseId>" are successfully applied

    Examples:
      | testCaseId |
      | SR_01      |

  # ── Excel-driven: toggle filters ─────────────────────────────────────────

  @filter_toggle
  Scenario Outline: Toggle bus type filters on and off - <testCaseId>
    When the user applies filters from excel row "<testCaseId>"
    And the user applies filters from excel row "<testCaseId>"
    Then the filters from excel row "<testCaseId>" are successfully toggled

    Examples:
      | testCaseId |
      | SR_03      |

  # ── Excel-driven: time filter select ─────────────────────────────────────

  @time_dropdown_select
  Scenario Outline: Select time slot from dropdown - <testCaseId>
    When the user selects time filter from excel row "<testCaseId>"
    Then the time filter from excel row "<testCaseId>" is applied successfully

    Examples:
      | testCaseId |
      | SR_06      |

  # ── Excel-driven: time filter deselect ───────────────────────────────────

  @time_dropdown_deselect
  Scenario Outline: Deselect time slot from dropdown - <testCaseId>
    When the user selects time filter from excel row "<testCaseId>"
    And the user clicks the time filter from excel row "<testCaseId>" again to deselect
    Then the time filter from excel row "<testCaseId>" is deselected successfully

    Examples:
      | testCaseId |
      | SR_06      |

  # ── Generalized Sort Scenarios (Excel Driven) ────────────────────────────

  @sort_options
  Scenario Outline: Toggle Sort by <testCaseId>
    When the user clicks sort by from excel row "<testCaseId>"
    And the user clicks sort by from excel row "<testCaseId>" again
    Then the sort by from excel row "<testCaseId>" is toggled successfully
    
    Examples:
      | testCaseId |
      | SR_07      |