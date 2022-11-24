package com.example.demo;


import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
 * 		@io.swagger.v3.oas.annotations.security.SecurityScheme(
 *        name = "basicAuth",
 *       type = SecuritySchemeType.HTTP,
 *       scheme = "basic")
 *   ------------------------------------------------------------------
 *		@io.swagger.v3.oas.annotations.security.SecurityScheme(name = "security_auth", type = SecuritySchemeType.OAUTH2,
 *	    flows = @OAuthFlows(authorizationCode = @OAuthFlow(
 *	            authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}"
 *	            , tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",scopes = {
 *           @OAuthScope(name = "IdentityPortal.API", description = "IdentityPortal.API")})))
 */

@Configuration
public class SwaggerAuthConfiguration {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerAuthConfiguration.class);

	private String tokenURL;
	
	private String serverURL;
	
	private String env;
	
    private String applicationName;
    
	private String apiKeyAuthHeaderName;
	
	@Autowired
	OpenAPI openAPI;
	
	@Bean
	public Server getServerInfo() {
		Server server = new Server();
	    server.setUrl(serverURL);
	    server.setDescription(env);
	    return server;
	}

	@Bean
	@Primary
	public OpenAPI mergeAuthentication(Server server) {
		LOGGER.info(String.format("%s", "Basic-Auth, Api-Key & OAuth"));
      	
		return openAPI
		  .servers(List.of(server))
          .components(new Components()
    		.addSecuritySchemes("Api_Key", 		apiKeySecuritySchema())
    		.addSecuritySchemes("Basic-Auth", 	basicSecuritySchema())
    		.addSecuritySchemes("OAuth", 		oauthSecuritySchema()))
          	.security(Arrays.asList(
          		new SecurityRequirement().addList("Api_Key"),
      			new SecurityRequirement().addList("Basic-Auth"),
      			new SecurityRequirement().addList("OAuth")
          	));
	}
	
	@Bean
    public OperationCustomizer customGlobalHeaders(OpenAPI openAPI) {
		
        return (Operation operation, HandlerMethod handlerMethod) -> {
        	// Global request Header
            operation.addParametersItem(IntegrationUtil.createParameter(HttpHeaders.ACCEPT_LANGUAGE, "List of acceptable human languages for response. E.g. en-GB", 
            		ParameterIn.HEADER, false));
            operation.addParametersItem(IntegrationUtil.createParameter("TraceID", "Id to trace the request. It is the same as passed in the request or an auto generated value", 
            		ParameterIn.HEADER, false));
            
            // Response Headers
            openAPI.getComponents()
            		.addHeaders(HttpHeaders.CACHE_CONTROL, IntegrationUtil.createResponseHeader("API response cache at client side", "max-age=0"));
            openAPI.getComponents()
    			.addHeaders(HttpHeaders.CONTENT_LANGUAGE,  IntegrationUtil.createResponseHeader("Response language", "en-GB"));
            
            return operation;
        };
	}
	
	private SecurityScheme basicSecuritySchema() {
		return new SecurityScheme()
		  .type(SecurityScheme.Type.HTTP)
		  .description("Basic Authentication")
		  .scheme("basic");
	}
	
	private SecurityScheme apiKeySecuritySchema() {
		return new SecurityScheme()
  				.type(SecurityScheme.Type.APIKEY)
  				.description("Api Key to access the Endpoint")
  				.in(SecurityScheme.In.HEADER)
  				.name(apiKeyAuthHeaderName);
	}
	
	private SecurityScheme oauthSecuritySchema() {
		return new SecurityScheme()
		  	.type(SecurityScheme.Type.OAUTH2)
		  	.description("Oauth2 Authentication Flow")
		  	.flows(new OAuthFlows()
	  			.clientCredentials(new OAuthFlow()
  					.authorizationUrl(tokenURL)
  					.tokenUrl(tokenURL)
  					 .scopes(new Scopes()
  						.addString("public", "Grants access to public API endpoints from the " + applicationName + " API")
  						.addString("internal","Grants access to internal API endpoints from the "+ applicationName + " API"))));
	} 
}