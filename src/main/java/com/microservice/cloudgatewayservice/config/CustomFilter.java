package com.microservice.cloudgatewayservice.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config>{

	public CustomFilter() {
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain)->{
			System.out.println("Pre Filter"+exchange.getRequest());
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				System.out.println("Post Filter");
			}));
			
		};
	}
	
	public static class Config{
		
	}

}
