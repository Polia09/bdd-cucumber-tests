Feature: Scroll to top functionality

  Scenario: Verify scroll to top button functionality
    Given User opens Intershop homepage
    When User scrolls to the bottom of the page
    Then Scroll to top button with id #ak-top should be visible
    When User clicks on scroll to top button
    Then Page should be scrolled to the top