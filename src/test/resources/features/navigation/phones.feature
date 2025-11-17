@navigation
@phones
Feature: Navigation in Intershop

  Scenario: Navigate to Phones section
    Given User opens Intershop homepage
    When User hovers over "Каталог" and selects "Электроника" -> "Телефоны"
    Then Page with title "Телефоны" is displayed


