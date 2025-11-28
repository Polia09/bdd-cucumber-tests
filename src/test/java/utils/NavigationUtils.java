package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class NavigationUtils {

    public static void navigateToSection(WebDriver driver, WebDriverWait wait, String menu, String submenu, String section) {
        Actions actions = new Actions(driver);

        try {
            Allure.step("Навигация: " + menu + " -> " + submenu + " -> " + section);

            Allure.step("Наводим курсор на меню: " + menu);
            WebElement catalogMenu = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), '" + menu + "')]")));
            actions.moveToElement(catalogMenu).pause(Duration.ofMillis(500)).perform();

            Allure.step("Наводим курсор на подменю: " + submenu);
            WebElement electronicsSubmenu = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), '" + submenu + "')]")));
            actions.moveToElement(electronicsSubmenu).pause(Duration.ofMillis(500)).perform();

            String actualSectionName = convertToActualSectionName(section);
            Allure.step("Ищем элемент с текстом: '" + actualSectionName + "'");

            Allure.step("Выбираем раздел: " + section);
            WebElement targetSection = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), '" + actualSectionName + "')]")));
            Allure.step("Найден элемент: " + targetSection.getText());
            targetSection.click();

            wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            Allure.step("Успешная навигация к: " + section);

        } catch (Exception e) {
            ScreenshotUtils.takeScreenshot(driver, "navigation_error_" + section, "errors");
            Allure.step("Ошибка навигации к разделу: " + section);
            throw e;
        }
    }

    private static String convertToActualSectionName(String section) {
        switch(section) {
            case "ТЕЛЕФОНЫ": return "Телефоны";
            case "ПЛАНШЕТЫ": return "Планшеты";
            case "ТЕЛЕВИЗОРЫ": return "Телевизоры";
            case "ФОТО/ВИДЕО": return "Фото/видео";
            case "ЧАСЫ": return "Часы";
            default: return section;
        }
    }

    public static void verifyPageTitle(WebDriver driver, String expectedTitle) {
        try {
            String actualTitle = driver.getTitle();
            boolean titleMatches = actualTitle.toLowerCase().contains(expectedTitle.toLowerCase());

            Allure.step("Проверяем заголовок страницы. Ожидаем: '" + expectedTitle + "', Фактический: '" + actualTitle + "'");

            assertTrue("Заголовок должен содержать: '" + expectedTitle + "', но был: '" + actualTitle + "'", titleMatches);
            Allure.step("Заголовок корректен: " + actualTitle);

        } catch (Exception e) {
            ScreenshotUtils.takeScreenshot(driver, "test_error_" + expectedTitle, "errors");
            Allure.step("Ошибка проверки заголовка: " + expectedTitle);
            throw e;
        }
    }
}