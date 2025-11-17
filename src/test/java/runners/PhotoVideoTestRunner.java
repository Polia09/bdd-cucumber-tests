package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/navigation",
        glue = {"steps", "hooks"},
        tags = "@photo_video",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/photo-video-report.html",
                "json:target/cucumber-reports/photo-video-report.json",
                "junit:target/cucumber-reports/photo-video-report.xml"
        },
        monochrome = true
)
public class PhotoVideoTestRunner {
}