@navigation
@tv
Feature: Navigation in Intershop

  Scenario: Navigate to TV section
    Given User opens Intershop homepage
    When User hovers over "Каталог" and selects "Электроника" -> "Телевизоры"
    Then Page with title "Телевизоры" is displayed


