package com.microservice.cloudgatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/customer/**")
						.uri("http://localhost:8081/")
						.id("customer"))
				.route(r -> r.path("/transaction-service/**")
						.uri("http://localhost:8082/")
						.id("transaction-service"))
				
				.build();
	}
}
