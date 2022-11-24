package com.example.demo;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;

public class IntegrationUtil {
	public static Parameter createParameter(String name, String description, ParameterIn type, boolean required) {
		return new Parameter().in(type.toString())
					.schema(new StringSchema())
					//.$ref(name)
					.name(name)
					.description(description)
					.required(required);
	}
	
	public static Header createResponseHeader(String description, String sample) {
		return new Header().description(description)
				.schema(new StringSchema()).example(sample);
	}
	
}