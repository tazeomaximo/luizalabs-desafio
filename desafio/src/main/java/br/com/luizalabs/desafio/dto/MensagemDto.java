package br.com.luizalabs.desafio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

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
