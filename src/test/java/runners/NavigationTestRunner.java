package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/features/navigation/phones.feature",
                "src/test/resources/features/navigation/photo_video.feature",
                "src/test/resources/features/navigation/tablets.feature",
                "src/test/resources/features/navigation/tv.feature",
                "src/test/resources/features/navigation/watches.feature"
        },
        glue = {"steps", "utils"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-navigation.html",
                "json:target/cucumber-reports/cucumber-navigation.json",
                "junit:target/cucumber-reports/cucumber-navigation.xml"
        },
        monochrome = true,
        publish = true
)
public class NavigationTestRunner {
}