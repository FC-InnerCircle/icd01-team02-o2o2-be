package org.example.o2o.config.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
			.info(new Info()
				.title("O2O Store API")
				.description("O2O 플랫폼의 스토어 관리 API 문서입니다.")
				.version("v1.0.0"));
	}
}
