package com.generation.blogpessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
@Configuration
public class SwaggerConfig {
	
	@Bean
	OpenAPI springBolgPessoaçOpenAPI () {
		return new OpenAPI ()
				.info(new Info()
						.title("Projeto Blog Pessoal do Matheus")
						.description("Projeto Blog Pessoal desenvolvido na Generation Brasil")
						.version("v0.0.1")
						.license(new License()
								.name("Matheus Alcântara")
								.url(""))
						.contact(new Contact()
								.name("Matheus Alcântara")
								.url("")
								.email("matheusalcantara717@gmail.com")))				
				.externalDocs(new ExternalDocumentation()
					.description("Github")
					.url("https://github.com/MatheusAlcn/BlogPessoal"));
	}
	
	@Bean 
	OpenApiCustomizer customerGlobalHeaderOpenApiCustomiser() {
		
		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations()
					.forEach(operation -> {
						
						ApiResponses apiResponses = operation.getResponses();
						
						apiResponses.addApiResponse("200", createApiresponse("Sucesso!"));
						apiResponses.addApiResponse("201", createApiresponse("Objeto Persisitido!"));
						apiResponses.addApiResponse("204", createApiresponse("Objeto Excluído!"));
						apiResponses.addApiResponse("400", createApiresponse("Erro na Requisição!"));
						apiResponses.addApiResponse("401", createApiresponse("Acesso não Autorizado!"));
						apiResponses.addApiResponse("403", createApiresponse("Acesso Proibido!"));
						apiResponses.addApiResponse("404", createApiresponse("Objeto não Encontrado!"));
						apiResponses.addApiResponse("500", createApiresponse("Erro na Aplicação"));
						
					})
					);
		};
	
	}

	private ApiResponse createApiresponse(String message) {
		return new ApiResponse().description(message);
	}	
}
