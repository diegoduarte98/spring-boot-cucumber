package io.tpd.springbootcucumber;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringBootCucumberApplication.class)
@Sql(value = "/matricula-load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(dataSource = "matricula"))
@Sql(value = "/matricula-clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(dataSource = "matricula"))
@Sql(value = "/produto-load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(dataSource = "produto"))
@Sql(value = "/produto-clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(dataSource = "produto"))
public abstract class SpringBootBaseIntegrationTest {

    private final String SERVER_URL = "http://localhost";
    private final String THINGS_ENDPOINT = "/things";

    private RestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    public SpringBootBaseIntegrationTest() {
        restTemplate = new RestTemplate();
    }

    private String thingsEndpoint() {
        return SERVER_URL + ":" + port + THINGS_ENDPOINT;
    }

    int put(final String something) {
        return restTemplate.postForEntity(thingsEndpoint(), something, Void.class).getStatusCodeValue();
    }

    Bag getContents() {
        return restTemplate.getForEntity(thingsEndpoint(), Bag.class).getBody();
    }

    void clean() {
        restTemplate.delete(thingsEndpoint());
    }
}
