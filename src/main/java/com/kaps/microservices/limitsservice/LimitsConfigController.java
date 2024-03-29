package com.kaps.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kaps.microservices.limitsservice.bean.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitsConfigController {
	
	@Autowired
	Configuration config;
	
	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitsFromConfiguration() {
		return new LimitConfiguration(config.getMaximum(), config.getMinimum());
	}
	
	@GetMapping("/fault-tolerance-sample")
	@HystrixCommand(fallbackMethod = "fallbackRetrieveConfiguration")
	public LimitConfiguration retrieveConfiguration() {
		throw new RuntimeException("FAULT_SIMULATION");
	}
	
	public LimitConfiguration fallbackRetrieveConfiguration() {
		return new LimitConfiguration(999, 9);
	}
}
