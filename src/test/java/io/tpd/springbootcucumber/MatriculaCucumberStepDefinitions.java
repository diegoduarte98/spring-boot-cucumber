package io.tpd.springbootcucumber;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.web.client.RestTemplate;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Ignore
public class MatriculaCucumberStepDefinitions extends SpringBootBaseIntegrationTest {
	private final String SERVER_URL = "http://localhost:8080/matricula-service";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	@Qualifier("jdbcTemplateMatricula")
	private JdbcTemplate jdbcTemplateMatricula;
	
	@Autowired
	@Qualifier("jdbcTemplateProduto")
	private JdbcTemplate jdbcTemplateProduto;
	
	@Before
	public void up() throws Throwable {
		ScriptUtils.executeSqlScript(jdbcTemplateMatricula.getDataSource().getConnection(),
				new ClassPathResource("matricula-load-database.sql"));

		ScriptUtils.executeSqlScript(jdbcTemplateProduto.getDataSource().getConnection(),
				new ClassPathResource("produto-load-database.sql"));
	}
	
	@When("^O cliente chama por /health$")
    public void the_client_issues_GET_health() throws Throwable {
		 restTemplate.getForEntity(SERVER_URL + "/health", String.class);
    }
	
	@Then("^O cliente recebe status code (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
		ResponseEntity<MatriculaIntegradaDTO> entity  = restTemplate.getForEntity(SERVER_URL + "/matriculas/11111111/beneficiarios-sobrevivencia", MatriculaIntegradaDTO.class);
		HttpStatus currentStatusCode = entity.getStatusCode();
		assertThat("status code deu rui, : " + entity.getBody(), currentStatusCode.value(), is(statusCode));
	}

	@After
	public void terminate() throws Throwable {
		ScriptUtils.executeSqlScript(jdbcTemplateMatricula.getDataSource().getConnection(),
				new ClassPathResource("matricula-clean-database.sql"));

		ScriptUtils.executeSqlScript(jdbcTemplateProduto.getDataSource().getConnection(),
				new ClassPathResource("produto-clean-database.sql"));
	}
}
