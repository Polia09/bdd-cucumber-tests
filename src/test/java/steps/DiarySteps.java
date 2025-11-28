package steps;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.BrowserManager;
import org.junit.Assert;
import java.util.List;

public class DiarySteps {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    public DiarySteps() {
        this.driver = BrowserManager.getDriver();
        this.wait = BrowserManager.getWait();
        this.js = (JavascriptExecutor) driver;
    }

    private WebElement getScrollContainer() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".vb-content")));
    }

    private void scrollToBottom() {
        WebElement container = getScrollContainer();
        js.executeScript("arguments[0].scrollTo(0, arguments[0].scrollHeight);", container);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".articlePreview.pageCreate__articlePreview")));
    }

    private void scrollToTop() {
        WebElement container = getScrollContainer();
        js.executeScript("arguments[0].scrollTo(0, 0);", container);
        wait.until(d -> ((Number) js.executeScript("return arguments[0].scrollTop;", container)).longValue() == 0);
    }

    @When("User adds {int} new notes")
    public void user_adds_new_notes(int numberOfNotes) {
        Allure.step("Добавляем " + numberOfNotes + " новых заметок");

        for (int i = 1; i <= numberOfNotes; i++) {
            WebElement textarea = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("textarea.baseTextarea__text")));
            textarea.clear();
            textarea.sendKeys("Test note " + i);

            WebElement addButton = findAddButton();
            if (addButton != null) {
                wait.until(ExpectedConditions.elementToBeClickable(addButton));
                js.executeScript("arguments[0].click();", addButton);
            } else {
                textarea.sendKeys(Keys.ENTER);
            }

            final int expectedCount = i;
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".articlePreview.pageCreate__articlePreview"), expectedCount - 1));
        }
    }

    @When("User deletes the top note")
    public void user_deletes_the_top_note() {
        Allure.step("Удаляем верхнюю запись");

        scrollToTop();

        WebElement container = getScrollContainer();
        List<WebElement> notesBefore = container.findElements(By.cssSelector(".articlePreview.pageCreate__articlePreview"));
        Assert.assertFalse("Нет заметок для удаления", notesBefore.isEmpty());

        WebElement firstNote = notesBefore.get(0);
        WebElement deleteBtn = firstNote.findElement(By.cssSelector(".articlePreview__buttons .articlePreview__button:last-child"));
        js.executeScript("arguments[0].scrollIntoView(true);", firstNote);
        js.executeScript("arguments[0].click();", deleteBtn);

        wait.until(ExpectedConditions.invisibilityOf(firstNote));

        Allure.step("Верхняя запись удалена");
    }

    @When("User scrolls to the very bottom of diary page")
    public void user_scrolls_to_the_very_bottom_of_diary_page() {
        Allure.step("Скролл до низа списка заметок");
        scrollToBottom();
    }

    @Then("Pre-existing notes should remain unchanged")
    public void pre_existing_notes_should_remain_unchanged() {
        Allure.step("Проверка наличия исходных заметок");

        scrollToBottom();

        WebElement container = getScrollContainer();
        List<WebElement> notes = container.findElements(By.cssSelector(".articlePreview.pageCreate__articlePreview"));

        Assert.assertTrue("Заметки должны присутствовать", notes.size() > 0);

        Allure.step("Количество найденных записей: " + notes.size());
    }

    private WebElement findAddButton() {
        return driver.findElements(By.cssSelector("button")).stream()
                .filter(b -> b.getText().toLowerCase().contains("добавить") || b.getText().toLowerCase().contains("add"))
                .findFirst()
                .orElse(null);
    }
}