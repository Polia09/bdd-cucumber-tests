package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.BrowserManager;
import utils.ScreenshotUtils;

import java.io.ByteArrayInputStream;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        BrowserManager.getDriver();
        System.out.println("Начало теста: " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                String screenshotName = "failed_" + scenario.getName().replace(" ", "_");
                ScreenshotUtils.takeScreenshot(BrowserManager.getDriver(), screenshotName, "errors");

                byte[] screenshot = ((TakesScreenshot) BrowserManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Скриншот при ошибке", "image/png", new ByteArrayInputStream(screenshot), ".png");

                System.err.println("Тест провален: " + scenario.getName());
            } else {
                System.out.println("Тест пройден: " + scenario.getName());
            }
        } catch (Exception e) {
            System.err.println("Ошибка в tearDown: " + e.getMessage());
        } finally {
            BrowserManager.closeBrowser();
        }
    }
}