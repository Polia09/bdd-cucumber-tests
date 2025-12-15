package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/features/navigation"
        },
        glue = {"steps", "utils"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-navigation.html",
                "json:target/cucumber-reports/cucumber-navigation.json",
                "junit:target/cucumber-reports/cucumber-navigation.xml",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true,
        publish = true
)
public class NavigationTestRunner {
}