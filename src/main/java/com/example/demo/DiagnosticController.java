package com.example.demo;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Diagnostic", description = "Ping, Status, Healthcheck and Interface endpoints")
public class DiagnosticController  {
	
	
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ping-Success",
        	headers = {
        		@Header(name = HttpHeaders.CACHE_CONTROL,    ref = HttpHeaders.CACHE_CONTROL),
    			@Header(name = HttpHeaders.CONTENT_LANGUAGE, ref = HttpHeaders.CONTENT_LANGUAGE)
        	},content = {
				@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))
        	})
    })
	@GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPing(
			@Parameter(schema = @Schema(hidden = true)) @RequestParam(required = false, name = "internal") boolean internal) {
    	return ResponseEntity.ok("Hai");
    }
    
   
}
