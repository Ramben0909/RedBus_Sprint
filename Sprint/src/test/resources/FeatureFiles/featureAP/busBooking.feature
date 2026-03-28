Feature: Bus Seat Booking Flow

  Scenario: Complete RedBus booking flow
    Given user opens redbus
    When user searches buses from "Delhi" to "Mumbai"
    And user selects a bus

    Then seat map is loaded
    And available seats should be clickable
    And booked seats should not be clickable

    When user selects seat "38L"
    And user selects seat "37L"
    Then total fare should be updated

    When user deselects seat "37L"
    Then fare should be reduced

    When user selects 6 seats
    And tries to select another seat
    Then warning message should appear

    When user selects boarding point and dropping point
    Then both should appear in summary

    When user enters valid passenger details
    Then user should proceed to payment

    When user enters contact details
    And selects UPI
    Then QR code should be displayed