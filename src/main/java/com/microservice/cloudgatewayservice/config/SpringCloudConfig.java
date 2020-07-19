package com.microservice.cloudgatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import reactor.core.publisher.Mono;

@Configuration
public class SpringCloudConfig {

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/customer/**")
						.filters(
								f -> f
									.modifyRequestBody(IncomingRequest.class, CustomerRequest.class, 
										(exchange, s) ->{
											return Mono.just(new CustomerRequest(s).invoke());
										}
										)
									.addRequestHeader("customer-req-header", "customer-request")
									.addResponseHeader("customer-resp-header", "customer-response")
								)
						.uri("http://localhost:8081/")
						.id("customer"))
				
				.route(r -> r.path("/transaction-service/**")
						.filters(
								f -> f.addRequestHeader("txnservice-req-header", "txnservice-request")
									.addResponseHeader("txnservice-resp-header", "txnservice-response")
								)
						.uri("http://localhost:8082/")
						.id("transaction-service"))
				
				.build();
	}
	
	@Data
	public static class IncomingRequest{
		private String id;
		private String name;
		private double salary;
	}
	
	@Data
	public static class CustomerRequest{
		private String number;
		private String firstName;
		private double salary;
		
		private IncomingRequest incomingRequest;
		
		public CustomerRequest(IncomingRequest incomingRequest) {
			this.incomingRequest = incomingRequest;
		}
		
		public CustomerRequest invoke() {
			this.setFirstName(incomingRequest.getName());
			this.setNumber(incomingRequest.getId());
			this.setSalary(incomingRequest.getSalary());
			return this;
		}
	}
}
