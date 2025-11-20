package utils;

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
            System.out.println("Навигация: " + menu + " -> " + submenu + " -> " + section);

            WebElement catalogMenu = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), '" + menu + "')]")));
            actions.moveToElement(catalogMenu).pause(Duration.ofMillis(500)).perform();

            WebElement electronicsSubmenu = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), '" + submenu + "')]")));
            actions.moveToElement(electronicsSubmenu).pause(Duration.ofMillis(500)).perform();

            String actualSectionName = convertToActualSectionName(section);
            System.out.println("Ищем элемент с текстом: '" + actualSectionName + "'");

            WebElement targetSection = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), '" + actualSectionName + "')]")));
            System.out.println("Найден элемент: " + targetSection.getText());
            targetSection.click();

            wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            System.out.println("Успешная навигация к: " + section);

        } catch (Exception e) {
            ScreenshotUtils.takeScreenshot(driver, "navigation_error_" + section, "errors");
            System.err.println("Ошибка навигации к: " + section);
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

            assertTrue("Заголовок должен содержать: '" + expectedTitle + "', но был: '" + actualTitle + "'", titleMatches);
            System.out.println("Заголовок корректен: " + actualTitle);

            ScreenshotUtils.takeScreenshot(driver, "test_success_" + expectedTitle, "success");

        } catch (Exception e) {
            ScreenshotUtils.takeScreenshot(driver, "test_error_" + expectedTitle, "errors");
            System.err.println("Ошибка проверки заголовка: " + expectedTitle);
            throw e;
        }
    }
}