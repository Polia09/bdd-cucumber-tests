package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/scroll",
        glue = {"steps", "utils"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/scroll-report.html",
                "json:target/cucumber-reports/scroll-report.json",
                "junit:target/cucumber-reports/scroll-report.xml",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class ScrollTestRunner {
}