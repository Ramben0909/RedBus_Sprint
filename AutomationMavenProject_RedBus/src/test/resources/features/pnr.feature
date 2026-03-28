@PNR
Feature: PNR Status Check

  @PNR @Excel
  Scenario: Verify PNR test data from Excel
    Given the PNR test data is loaded from Excel sheet "PNR_Data"

  Scenario: Valid 10-digit PNR shows booking status
    Given the user is on the Train Running Status page
    When the user navigates to PNR status section
    And the user enters PNR number "6700914458"
    And the user clicks on Check Status
    Then the PNR details page should be displayed

  Scenario: Invalid 10-digit PNR shows error message
    Given the user is on the Train Running Status page
    When the user navigates to PNR status section
    And the user enters PNR number "3476986918"
    And the user clicks on Check Status
    Then an error message should be displayed for invalid PNR

  Scenario: Less than 10-digit PNR disables button
    Given the user is on the Train Running Status page
    When the user navigates to PNR status section
    And the user enters PNR number "12345"
    Then the Check Status button should remain disabled

  Scenario: More than 10-digit PNR is restricted
    Given the user is on the Train Running Status page
    When the user navigates to PNR status section
    And the user enters PNR number "1234567890123"
    Then the PNR input should accept only 10 digits
