Feature: Live Train Running Status on RedBus
  As a user I want to search and track trains and check seat availability
  So that I can plan and monitor my journey

  Background:
    Given the user is on the RedBus train running status page

  # ─────────────────────────────────────────────────────────────────────
  # EXCEL VALIDATION SCENARIO
  # ─────────────────────────────────────────────────────────────────────
  @ExcelRead
  Scenario: Verify test data can be loaded from Excel using Apache POI
    Given the test data is loaded from Excel sheet "TrainSearchData"

  # ─────────────────────────────────────────────────────────────────────
  # STORY 1 — SEARCH (VALID CASES)
  # ─────────────────────────────────────────────────────────────────────
  @Search
  Scenario Outline: Search with valid train input and verify details page opens
    When the user searches for "<trainInput>"
    Then the train details page should be displayed

    Examples:
      | trainInput       | tcId  | description         |
      | 123              | TC_01 | Partial number      |
      | 12301            | TC_02 | Exact train number  |
      | Rajd             | TC_03 | Partial name string |
      | Rajdhani Express | TC_04 | Full train name     |

  # ─────────────────────────────────────────────────────────────────────
  # STORY 1 — SEARCH (NEGATIVE CASES)
  # ─────────────────────────────────────────────────────────────────────
  @Search
  Scenario Outline: Search with invalid input keeps Check Status button disabled
    When the user types "<invalidInput>" in the search box
    Then the Check Status button should be disabled

    Examples:
      | invalidInput | tcId  | description               |
      | 99999        | TC_07 | Non-existent train number |
      | XXXXXX       | TC_08 | Non-existent name string  |

  # ─────────────────────────────────────────────────────────────────────
  # STORY 3 — AVAILABILITY FLOW
  # ─────────────────────────────────────────────────────────────────────
  @Availability
  Scenario: Check seat availability from Top 10 trains list
    When the user performs availability steps
      | click availability     |
      | navigate to next month |
      | select date            |
    Then the seat availability result should be displayed