package com.cognixia.jump.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * On Browser, go to URL: http://localhost:8080/swagger-ui.html
 * 
 * On Postman, do a get request to http://localhost:8080/v2/api-docs
 * 
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
					.paths(PathSelectors.any())
					.build()
					.apiInfo(apiDetails());
	}
	
	private ApiInfo apiDetails() {
		
		return new ApiInfo("Review API", 
				"API for a database allowing users to login and leave reviews for restaurants, or just browse reviews", 
				"1.0",
				"Free to use", 
				new Contact("Austin, Raymond, Sabeet, Nikita","https://github.com/JUMP-goat/JUMP-Final-Project", "73669412+abrooks72@users.noreply.github.com, sudoheader@users.noreply.github.com, 30205922+sabeet@users.noreply.github.com, 26977305+nikitatran@users.noreply.github.com"),
				"MIT License",
				"https://www.mit.edu/~amini/LICENSE.md",
				Collections.emptyList()
				);
	}
}
