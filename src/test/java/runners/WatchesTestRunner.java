package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/navigation",
        glue = {"steps", "hooks"},
        tags = "@watches or @Watches",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/watches-report.html",
                "json:target/cucumber-reports/watches-report.json",
                "junit:target/cucumber-reports/watches-report.xml"
        },
        monochrome = true
)
public class WatchesTestRunner {
}