Feature: Live Train Running Status on RedBus
  As a user I want to search and track trains and check seat availability
  So that I can plan and monitor my journey

  Background:
    Given the user is on the RedBus train running status page

  # ─────────────────────────────────────────────────────────────────────
  # STORY 1 — TC_01, TC_02, TC_03, TC_04
  # Valid search inputs: partial number, exact number, partial name, full name
  # All 4 rows run independently; each opens browser via Hooks @Before
  # ─────────────────────────────────────────────────────────────────────
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
  # STORY 1 — TC_07, TC_08
  # Negative: wrong/non-existent number or string — no suggestions,
  # Check Status button stays disabled
  # ─────────────────────────────────────────────────────────────────────
  Scenario Outline: Search with invalid input keeps Check Status button disabled
    When the user types "<invalidInput>" in the search box
    Then the Check Status button should be disabled

    Examples:
      | invalidInput | tcId  | description               |
      | 99999        | TC_07 | Non-existent train number |
      | XXXXXX       | TC_08 | Non-existent name string  |

  # ─────────────────────────────────────────────────────────────────────
  # STORY 3 — TC_16, TC_18, TC_19/TC_20 (seat availability full flow)
  # Uses DataTable to pass one train number and three action steps cleanly.
  # Flow: search → details page → availability button → next month → pick date → result
  # ─────────────────────────────────────────────────────────────────────
  Scenario: Check seat availability from Top 10 trains list
    Given the user is on the RedBus train running status page
    When the user performs availability steps
      | click availability     |
      | navigate to next month |
      | select date            |
    Then the seat availability result should be displayed
