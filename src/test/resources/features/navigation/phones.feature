@navigation
@phones
Feature: Navigation in Intershop

  Scenario: Navigate to Phones section
    Given User opens Intershop homepage
    When User hovers over "Каталог" and selects "Электроника" -> "ТЕЛЕФОНЫ"
    Then Page with title "ТЕЛЕФОНЫ" is displayed


