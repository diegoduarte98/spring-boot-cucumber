package io.tpd.springbootcucumber;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringBootCucumberApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCucumberApplication.class, args);
    }
    
    @Bean
    public RestTemplate restTemplate(HttpServletRequest httpServletRequest) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjYW5hbFNvbGljaXRhY2FvIjoiMzAiLCJpc3MiOiJicmFzaWxwcmV2LmNvbS5iciIsImNhbmFsVmVuZGEiOiIzMCIsImV4cCI6MTU4MzY5Mjc2NywiaWF0IjoxNTUyMTU2NzY3LCJjYW5hbERpc3RyaWJ1aWNhbyI6IjMwIn0.mBXapdaF2CrdCfVbC8bUE7hJF5vAKH5ITEF_xEyiDOs");
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
