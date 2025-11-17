package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/navigation",
        glue = {"steps", "hooks"},
        tags = "@tv or @TV or @television",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/tv-report.html",
                "json:target/cucumber-reports/tv-report.json",
                "junit:target/cucumber-reports/tv-report.xml"
        },
        monochrome = true
)
public class TvTestRunner {
}