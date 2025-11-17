@navigation
@photo_video
Feature: Navigation in Intershop

  Scenario: Navigate to Photo/Video section
    Given User opens Intershop homepage
    When User hovers over "Каталог" and selects "Электроника" -> "Фото и видео"
    Then Page with title "Фото/видео" is displayed

