package io.tpd.springbootcucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/bag.feature", plugin = {"pretty", "html:target/cucumber"})
public class BagCucumberIntegrationTest {
}
