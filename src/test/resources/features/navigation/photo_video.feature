@navigation
@photo_video
Feature: Navigation in Intershop

  Scenario: Navigate to Photo/Video section
    Given User opens Intershop homepage
    When User hovers over "Каталог" and selects "Электроника" -> "ФОТО/ВИДЕО"
    Then Page with title "ФОТО/ВИДЕО" is displayed



