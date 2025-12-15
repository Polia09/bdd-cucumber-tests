package steps;

import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import utils.BrowserManager;

public class BaseSteps {
    private WebDriver driver;

    public BaseSteps() {
        this.driver = BrowserManager.getDriver();
    }

    @Given("User opens Intershop homepage")
    public void user_opens_intershop_homepage() {
        driver.get("https://intershop5.skillbox.ru/");
    }

    @Given("User opens diary page")
    public void user_opens_diary_page() {
        driver.get("https://qa.skillbox.ru/module15/bignotes/#/");
    }
}