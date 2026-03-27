Feature: Train Search Functionality (redRail)

  Scenario Outline: User searches trains with valid source and destination
    Given User is on redRail homepage
    When User clicks on Train Tickets
    And User loads Excel data id "<data_id>" from "ValidSearch"
    And User enters loaded From field
    And User selects loaded From suggestion
    And User enters loaded To field
    And User selects loaded To suggestion
    And User clicks on Search Trains button
    Then Train results should be displayed

    Examples: Valid train routes
      | data_id |
      | VS_01   |
      | VS_02   |
      | VS_03   |

  Scenario Outline: User selects date and searches trains
    Given User is on redRail homepage
    When User clicks on Train Tickets
    And User loads Excel data id "<data_id>" from "DateSearch"
    And User enters loaded From field
    And User selects loaded From suggestion
    And User enters loaded To field
    And User selects loaded To suggestion
    And User opens Date of Journey calendar
    And User selects loaded date
    And User clicks on Search Trains button
    Then Train results should be displayed

    Examples: Routes with different dates
      | data_id |
      | DS_01   |
      | DS_02   |
      | DS_03   |

  Scenario Outline: User searches trains with an invalid destination
    Given User is on redRail homepage
    When User clicks on Train Tickets
    And User loads Excel data id "<data_id>" from "InvalidSearch"
    And User enters loaded From field
    And User selects loaded From suggestion
    And User enters loaded invalid destination
    Then No suggestions should be displayed for invalid destination

    Examples: Invalid destinations
      | data_id |
      | IS_01   |
      | IS_02   |
      | IS_03   |