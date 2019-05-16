package io.tpd.springbootcucumber;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MatriculaDBConfig {

	@Autowired
	private Environment env;

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean matriculaEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(matriculaDataSource());
		em.setPackagesToScan(new String[] { "io.tpd.springbootcucumber" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
		properties.put("hibernate.temp.use_jdbc_metadata_defaults",
				env.getProperty("spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults"));
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Primary
	@Bean(name = "matricula")
	public DataSource matriculaDataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("spring.matricula.datasource.driver-class-name"));
		dataSource.setUrl(env.getProperty("spring.matricula.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.matricula.datasource.username"));
		dataSource.setPassword(env.getProperty("spring.matricula.datasource.password"));

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager matriculaTransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(matriculaEntityManager().getObject());
		return transactionManager;
	}
	
	@Primary
	@Bean("jdbcTemplateMatricula")
	public JdbcTemplate jdbcTemplateMatricula() {
		return new JdbcTemplate(matriculaDataSource());
	}

}
