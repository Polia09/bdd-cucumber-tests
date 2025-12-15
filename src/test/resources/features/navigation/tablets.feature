@navigation
@tablets
Feature: Navigation in Intershop

  Scenario: Navigate to Tablets section
    Given User opens Intershop homepage
    When User hovers over "Каталог" and selects "Электроника" -> "ПЛАНШЕТЫ"
    Then Page with title "ПЛАНШЕТЫ" is displayed