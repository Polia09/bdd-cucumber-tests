package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.BrowserManager;
import utils.ScreenshotUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        BrowserManager.getDriver();
        logger.info("Начало теста: {}", scenario.getName());
        logger.debug("ID сценария: {}", scenario.getId());
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                String screenshotName = "failed_" + scenario.getName().replaceAll("[^a-zA-Z0-9_-]", "_");
                ScreenshotUtils.takeScreenshot(BrowserManager.getDriver(), screenshotName, "errors");

                byte[] screenshot = ((TakesScreenshot) BrowserManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Скриншот при ошибке", "image/png", new ByteArrayInputStream(screenshot), ".png");

                logger.error("Тест ПРОВАЛЕН: {}", scenario.getName());
                logger.error("Статус: {}", scenario.getStatus());
            } else {
                logger.info("Тест УСПЕШНО пройден: {}", scenario.getName());
                logger.info("Статус: {}", scenario.getStatus());
            }
        } catch (Exception e) {
            logger.error("Ошибка в методе tearDown: {}", e.getMessage(), e);
        } finally {
            try {
                BrowserManager.closeBrowser();
                logger.debug("Браузер закрыт");
            } catch (Exception e) {
                logger.error("Ошибка при закрытии браузера: {}", e.getMessage());
            }
        }
    }
}