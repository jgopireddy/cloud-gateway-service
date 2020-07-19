package com.microservice.cloudgatewayservice.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;

@Configuration
public class CustomGlobalFilter {

	@Bean
	public GlobalFilter globalFilter() {
		return (exchange, chain) ->{
			System.out.println("First Global filter");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				System.out.println("Second Global filter");
			}));
		};
		
	}
}
