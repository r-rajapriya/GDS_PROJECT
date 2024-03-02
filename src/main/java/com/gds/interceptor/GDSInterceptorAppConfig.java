package com.gds.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Config class for application configuration
 * 
 * @author Rajapriya
 *
 */
@Configuration
public class GDSInterceptorAppConfig implements WebMvcConfigurer {

	   @Override
	   public void addInterceptors(InterceptorRegistry registry) {
	      registry.addInterceptor(new RequestInterceptor());
	   }
}
