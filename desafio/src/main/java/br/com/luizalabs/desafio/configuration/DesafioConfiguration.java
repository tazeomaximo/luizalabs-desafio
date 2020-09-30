package br.com.luizalabs.desafio.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class DesafioConfiguration {
	
	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("API Clientes - Produtos Favoritos")
		    .description("Essa API serve para cadastrar cliente e seus produtos favoritos")
		    .termsOfServiceUrl("")
		    .version("v1.0")
		    .contact(new Contact("Luiz Darbem", "", "gutodarbem@gmail.com"))
		    .build();
	}

	@Bean
	public Docket customImplementation() {
		return new Docket(DocumentationType.SWAGGER_2).select()
		    .apis(RequestHandlerSelectors.basePackage("br.com.luizalabs.desafio.controller"))
		    .build()
		    .apiInfo(this.apiInfo());
	}

	

}