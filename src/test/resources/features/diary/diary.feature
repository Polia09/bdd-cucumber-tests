Feature: Diary functionality testing

  Scenario: Add and remove notes in diary
    Given User opens diary page
    When User adds 10 new notes
    And User deletes the top note
    And User scrolls to the very bottom of diary page
    Then Pre-existing notes should remain unchanged