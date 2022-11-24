package com.example.demo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class ApiGateway1Application {

	public static void main(String[] args) {

		new SpringApplicationBuilder(ApiGateway1Application.class)
		.web(WebApplicationType.REACTIVE)
		.run(args);
		
	}
	
    @Bean
    public OpenAPI defaultOpenAPI() {
		return new OpenAPI().info(new Info()
				.title("Title - API")
				.description("Description")
				.version("v1.0.0")
				.contact(new Contact().name("Contact Team").email("email")))
				.externalDocs(new ExternalDocumentation().description("desc")
						.url("GIT"));
	}
    
//    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ping-Success",
//        	headers = {
//        		@Header(name = HttpHeaders.CACHE_CONTROL,    ref = HttpHeaders.CACHE_CONTROL),
//    			@Header(name = HttpHeaders.CONTENT_LANGUAGE, ref = HttpHeaders.CONTENT_LANGUAGE)
//        	},content = {
//				@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Ping.class))
//        	})
//    })
//	@GetMapping(value = "/_test", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> getPing(
//			@Parameter(schema = @Schema(hidden = true)) @RequestParam(required = false, name = "internal") boolean internal) {
//    		return ResponseEntity.ok("done");
//    }
//    
//	@Bean
//	@Primary
//    public GlobalOperationCustomizer customGlobalHeaders(OpenAPI openAPI) {
//		
//        return (Operation operation, HandlerMethod handlerMethod) -> {
//        	// Global request Header
//            operation.addParametersItem(IntegrationUtil.createParameter(HttpHeaders.ACCEPT_LANGUAGE, "List of acceptable human languages for response. E.g. en-GB", 
//            		ParameterIn.HEADER, false));
//            operation.addParametersItem(IntegrationUtil.createParameter(IntegrationConstants.TRACE_ID, "Id to trace the request. It is the same as passed in the request or an auto generated value", 
//            		ParameterIn.HEADER, false));
//            
//            // Response Headers
//            openAPI.getComponents()
//            		.addHeaders(HttpHeaders.CACHE_CONTROL, IntegrationUtil.createResponseHeader("API response cache at client side", "max-age=0"));
//            openAPI.getComponents()
//    			.addHeaders(HttpHeaders.CONTENT_LANGUAGE,  IntegrationUtil.createResponseHeader("Response language", "en-GB"));
//            
//            return operation;
//        };
//	}
	

}
