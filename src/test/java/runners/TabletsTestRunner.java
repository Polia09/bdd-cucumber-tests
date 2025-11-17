package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/navigation",
        glue = {"steps", "hooks"},
        tags = "@tablets or @Tablets",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/tablets-report.html",
                "json:target/cucumber-reports/tablets-report.json",
                "junit:target/cucumber-reports/tablets-report.xml"
        },
        monochrome = true
)
public class TabletsTestRunner {
}