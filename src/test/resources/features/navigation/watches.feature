@navigation
@watches
Feature: Navigation in Intershop

  Scenario: Navigate to Watches section
    Given User opens Intershop homepage
    When User hovers over "Каталог" and selects "Электроника" -> "Часы"
    Then Page with title "Часы" is displayed