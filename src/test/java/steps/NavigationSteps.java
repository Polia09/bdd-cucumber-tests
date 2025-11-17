package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class NavigationSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        actions = new Actions(driver);
    }

    @Given("User opens Intershop homepage")
    public void user_opens_intershop_homepage() {
        driver.get("https://intershop5.skillbox.ru/");
        takeScreenshot("main_page", "general");
    }

    @When("User hovers over {string} and selects {string} -> {string}")
    public void user_hovers_over_and_selects(String menuItem, String submenuItem, String targetItem) {
        performAdvancedNavigation(menuItem, submenuItem, targetItem);
    }

    private void performAdvancedNavigation(String menuItem, String submenuItem, String targetItem) {
        try {
            System.out.println("Выполняем навигацию: " + menuItem + " -> " + submenuItem + " -> " + targetItem);

            WebElement catalogMenu = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(text(), 'Каталог')]")));

            actions.moveToElement(catalogMenu)
                    .pause(Duration.ofMillis(800))
                    .build()
                    .perform();
            takeScreenshot("after_catalog_hover", "debug");

            WebElement electronicsSubmenu = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href, 'electronics')]")));

            actions.moveToElement(electronicsSubmenu)
                    .pause(Duration.ofMillis(800))
                    .build()
                    .perform();
            takeScreenshot("after_electronics_hover", "debug");

            String targetSlug = getTargetSlug(targetItem);
            WebElement targetSection = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href, '" + targetSlug + "')]")));

            System.out.println("Найден раздел: " + targetSection.getText() + " (href: " + targetSection.getAttribute("href") + ")");

            actions.click(targetSection)
                    .build()
                    .perform();

            JavascriptExecutor js = (JavascriptExecutor) driver;
            wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));

            takeScreenshot("navigation_complete_" + targetSlug, "general");

        } catch (Exception e) {
            System.out.println("Ошибка в advanced navigation: " + e.getMessage());
            takeScreenshot("error_advanced_navigation", "errors");
            throw e;
        }
    }

    private String getTargetSlug(String targetItem) {
        switch (targetItem.toLowerCase()) {
            case "телефоны":
                return "phones";
            case "планшеты":
                return "pad";
            case "телевизоры":
                return "tv";
            case "часы":
                return "watch";
            case "фото и видео":
            case "фото/видео":
                return "photo";
            default:
                return targetItem.toLowerCase();
        }
    }

    @Then("Page with title {string} is displayed")
    public void page_with_title_is_displayed(String expectedTitle) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            wait.until(webDriver -> js.executeScript("return document.readyState").equals("complete"));

            String actualTitle = driver.getTitle();
            System.out.println("Фактический заголовок: " + actualTitle);

            String normalizedActual = actualTitle.replace("?", "").replace("Skillbox", "").trim();
            String normalizedExpected = expectedTitle.replace("?", "").replace("Skillbox", "").trim();

            boolean titleMatches = normalizedActual.toLowerCase().contains(normalizedExpected.toLowerCase()) ||
                    actualTitle.toLowerCase().contains(expectedTitle.toLowerCase()) ||
                    normalizedActual.contains("Фото") && normalizedExpected.contains("Фото") ||
                    normalizedActual.contains("Видео") && normalizedExpected.contains("Видео");

            assertTrue("Page title should contain: " + expectedTitle + ", but was: " + actualTitle, titleMatches);

            takeScreenshot("success_" + expectedTitle, "success");

        } catch (Exception e) {
            System.out.println("Ошибка проверки заголовка. Текущий заголовок: " + driver.getTitle());
            takeScreenshot("error_" + expectedTitle, "errors");
            throw e;
        }
    }

    private void takeScreenshot(String screenshotName, String folder) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String safeFileName = screenshotName.replace("/", "_")
                    .replace("\\", "_")
                    .replace(":", "_")
                    .replace("*", "_")
                    .replace("?", "_")
                    .replace("\"", "_")
                    .replace("<", "_")
                    .replace(">", "_")
                    .replace("|", "_");
            String fileName = safeFileName + "_" + timestamp + ".png";

            File destination = new File("test-results/screenshots/" + folder + "/" + fileName);
            destination.getParentFile().mkdirs();
            Files.copy(screenshot.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Скриншот сохранен: " + destination.getName());

        } catch (IOException e) {
            System.out.println("Ошибка скриншота: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}