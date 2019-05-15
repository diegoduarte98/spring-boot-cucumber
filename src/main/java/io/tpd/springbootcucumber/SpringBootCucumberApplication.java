package io.tpd.springbootcucumber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class SpringBootCucumberApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCucumberApplication.class, args);
    }

    @Bean(name = "matricula")
    @ConfigurationProperties(prefix = "spring.matricula")
    public DataSource matriculaDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "produto")
    @ConfigurationProperties(prefix = "spring.produto")
    public DataSource produtoDataSource() {
        return DataSourceBuilder.create().build();
    }
}
