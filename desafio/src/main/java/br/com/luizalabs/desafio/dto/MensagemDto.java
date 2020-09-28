package br.com.luizalabs.desafio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel("Mensagem")
@JsonPropertyOrder(value = { "codigo", "mensagem" })
public class MensagemDto {

	@ApiParam(type = "string", name = "codigo", example = "Código da mensagem")
	@JsonProperty("codigo")
	private String codigo;

	@ApiParam(type = "string", name = "mensagem", example = "Mensagem informativa")
	@JsonProperty("mensagem")
	private String mensagem;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public String toString() {
		return "MensagemDto [codigo=" + codigo + ", mensagem=" + mensagem + "]";
	}

}
