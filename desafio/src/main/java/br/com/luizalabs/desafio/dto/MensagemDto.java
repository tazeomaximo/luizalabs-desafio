package br.com.luizalabs.desafio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(content = Include.NON_NULL)
@ApiModel("Mensagem")
@JsonPropertyOrder(value = { "codigo", "mensagem"})
public class MensagemDto {
	
	@ApiParam(type = "string", name = "codigo", example = "Código da mensagem")
	@JsonProperty("codigo")
	private String codigo;
	
	@ApiParam(type = "string", name = "mensagem", example = "Mensagem informativa")
	@JsonProperty("mensagem")
	private String mensagem;

}
