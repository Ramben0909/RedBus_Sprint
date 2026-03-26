Feature: Bus Seat Booking Flow

Background:
  Given user opens redbus and logs in manually
  When user searches buses from "Delhi" to "Mumbai"
  And user selects a bus

Scenario: Validate seat map display and selection
  Then seat map is loaded
  And available seats should be clickable
  And booked seats should not be clickable

Scenario: Validate seat selection and fare
  When user selects seat "38L"
  And user selects seat "37L"
  Then total fare should be updated

Scenario: Validate seat deselection
  When user deselects seat "37L"
  Then fare should be reduced

Scenario: Validate max seat selection
  When user selects 6 seats
  And tries to select another seat
  Then warning message should appear

Scenario: Validate boarding and dropping selection
  When user selects boarding point
  And user selects dropping point
  Then both should appear in summary

Scenario: Validate passenger details
  When user enters valid passenger details
  Then user should proceed to payment

Scenario: Validate invalid passenger details
  When user enters invalid data
  Then error message should be displayed

Scenario: Validate payment flow
  When user enters contact details
  And selects UPI
  Then QR code should be displayed