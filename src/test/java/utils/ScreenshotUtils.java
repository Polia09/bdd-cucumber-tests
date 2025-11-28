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

public class ScreenshotUtils {

    public static void takeScreenshot(WebDriver driver, String screenshotName, String folder) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String safeFileName = convertToEnglish(screenshotName);
            String fileName = safeFileName + "_" + timestamp + ".png";

            File destination = new File("target/screenshots/" + folder + "/" + fileName);
            destination.getParentFile().mkdirs();
            Files.copy(screenshot.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Скриншот сохранен: " + destination.getName());

        } catch (IOException e) {
            System.out.println("Ошибка скриншота: " + e.getMessage());
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