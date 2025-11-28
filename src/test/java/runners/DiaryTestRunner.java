package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/diary",
        glue = {"steps", "utils"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/diary-report.html",
                "json:target/cucumber-reports/diary-report.json",
                "junit:target/cucumber-reports/diary-report.xml",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
)
public class DiaryTestRunner {
}