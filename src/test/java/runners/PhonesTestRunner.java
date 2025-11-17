package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/navigation/phones.feature",
        glue = "steps",
        plugin = {"pretty", "html:target/cucumber-reports-phones.html"},
        monochrome = true
)
public class PhonesTestRunner {
}