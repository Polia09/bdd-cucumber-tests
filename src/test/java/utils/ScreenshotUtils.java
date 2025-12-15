package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScreenshotUtils {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);

    public static void takeScreenshot(WebDriver driver, String screenshotName, String folder) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String safeFileName = convertToEnglish(screenshotName);
            String fileName = safeFileName + "_" + timestamp + ".png";

            File destination = new File("target/screenshots/" + folder + "/" + fileName);
            destination.getParentFile().mkdirs();
            Files.copy(screenshot.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

            logger.info("Скриншот сохранен: {}", destination.getAbsolutePath());
            logger.debug("Имя файла: {}, Размер: {} bytes", fileName, screenshot.length());

        } catch (IOException e) {
            logger.error("Ошибка при сохранении скриншота: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при создании скриншота: {}", e.getMessage(), e);
        }
    }

    private static String convertToEnglish(String text) {
        return text
                .replace("ТЕЛЕФОНЫ", "PHONES")
                .replace("ПЛАНШЕТЫ", "TABLETS")
                .replace("ТЕЛЕВИЗОРЫ", "TV")
                .replace("ФОТО/ВИДЕО", "PHOTO_VIDEO")
                .replace("ЧАСЫ", "WATCHES")
                .replace(" ", "_")
                .replace("/", "_")
                .replaceAll("[^a-zA-Z0-9-_]", "_");
    }
}