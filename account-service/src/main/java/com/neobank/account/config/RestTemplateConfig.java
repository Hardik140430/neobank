package com.neobank.account.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean
    @LoadBalanced // ⬅️ Enables Eureka-based service discovery by service name
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
