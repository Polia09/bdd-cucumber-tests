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
import static utils.Section.convert;
import static org.assertj.core.api.Assertions.assertThat;

public class NavigationUtils {
    private static final String MENU_XPATH = "//a[contains(text(), '%s')]";

    public static void navigateToSection(WebDriver driver, WebDriverWait wait, String menu, String submenu, String section) {
        Actions actions = new Actions(driver);

        try {
            Allure.step("Навигация: " + menu + " -> " + submenu + " -> " + section);

            Allure.step("Наводим курсор на меню: " + menu);
            WebElement catalogMenu = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(String.format(MENU_XPATH, menu))));
            actions.moveToElement(catalogMenu).pause(Duration.ofMillis(500)).perform();

            Allure.step("Наводим курсор на подменю: " + submenu);
            WebElement electronicsSubmenu = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(String.format(MENU_XPATH, submenu))));
            actions.moveToElement(electronicsSubmenu).pause(Duration.ofMillis(500)).perform();

            String actualSectionName = Section.convert(section);
            Allure.step("Ищем элемент с текстом: '" + actualSectionName + "'");

            Allure.step("Выбираем раздел: " + section);
            WebElement targetSection = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(String.format(MENU_XPATH, actualSectionName))));
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

    public static void verifyPageTitle(WebDriver driver, String expectedTitle) {
            String actualTitle = driver.getTitle();

            Allure.step("Проверяем заголовок страницы. Ожидаем: '" + expectedTitle + "', Фактический: '" + actualTitle + "'");

            assertThat(actualTitle.toLowerCase())
                    .as("Проверка что заголовок содержит: " + expectedTitle)
                    .contains(expectedTitle.toLowerCase());
            Allure.step("Заголовок корректен: " + actualTitle);
    }
}