package steps;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BrowserManager;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ScrollSteps {
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public ScrollSteps() {
        this.wait = BrowserManager.getWait();
        this.js = (JavascriptExecutor) BrowserManager.getDriver();
    }

    @When("User scrolls to the bottom of the page")
    public void user_scrolls_to_the_bottom_of_the_page() {
        Allure.step("Скроллим страницу до самого низа");
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    @Then("Scroll to top button with id #ak-top should be visible")
    public void scroll_to_top_button_with_id_ak_top_should_be_visible() {
        Allure.step("Проверяем видимость кнопки скролла наверх с id #ak-top");

        WebElement scrollToTopButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("ak-top"))
        );

        Assert.assertTrue("Кнопка скролла наверх с id #ak-top должна быть видимой",
                scrollToTopButton.isDisplayed());
        Allure.step("Кнопка скролла наверх с id #ak-top отображается корректно");
    }

    @When("User clicks on scroll to top button")
    public void user_clicks_on_scroll_to_top_button() {
        Allure.step("Нажимаем на кнопку скролла наверх с id #ak-top");

        WebElement scrollToTopButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("ak-top"))
        );
        js.executeScript("arguments[0].click();", scrollToTopButton);
    }

    @Then("Page should be scrolled to the top")
    public void page_should_be_scrolled_to_the_top() {
        Allure.step("Проверяем, что страница проскроллена к верху");

        wait.until(driver -> {
            Long currentPosition = (Long) js.executeScript("return window.pageYOffset;");
            return currentPosition <= 50;
        });

        Long finalPosition = (Long) js.executeScript("return window.pageYOffset;");
        Assert.assertTrue("Страница должна быть проскроллена к верху. Текущая позиция: " + finalPosition,
                finalPosition <= 50);
        Allure.step("Страница успешно проскроллена к верху. Позиция: " + finalPosition);
    }
}