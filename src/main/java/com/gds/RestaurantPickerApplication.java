package com.gds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.Async;

/**
 * Restaurant Picker Application to collect team members choice of Restaurants and 
 * Choose the one randomly for team lunch event
 * 
 * @author Rajapriya
 *
 */
@SpringBootApplication
public class RestaurantPickerApplication extends SpringBootServletInitializer{
	
	private static final Logger log = LogManager.getLogger(RestaurantPickerApplication.class);
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RestaurantPickerApplication.class);
    }
    
	public static void main(String[] args) {
		SpringApplication.run(RestaurantPickerApplication.class, args);		
		log.info("Restaurant Picker Application is started....");
	}
}
