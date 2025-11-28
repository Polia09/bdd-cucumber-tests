package steps;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BrowserManager;
import utils.NavigationUtils;

public class NavigationSteps {
    private WebDriver driver;
    private WebDriverWait wait;

    public NavigationSteps() {
        this.driver = BrowserManager.getDriver();
        this.wait = BrowserManager.getWait();
    }

    @When("User hovers over {string} and selects {string} -> {string}")
    public void user_hovers_over_and_selects(String menu, String submenu, String section) {
        NavigationUtils.navigateToSection(driver, wait, menu, submenu, section);
    }

    @Then("Page with title {string} is displayed")
    public void page_with_title_is_displayed(String expectedTitle) {
        NavigationUtils.verifyPageTitle(driver, expectedTitle);
    }
}