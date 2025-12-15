@navigation
@watches
Feature: Navigation in Intershop

  Scenario: Navigate to Watches section
    Given User opens Intershop homepage
    When User hovers over "Каталог" and selects "Электроника" -> "ЧАСЫ"
    Then Page with title "ЧАСЫ" is displayed