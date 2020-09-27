package br.com.luizalabs.desafio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel("Cliente")
@JsonPropertyOrder(value = { "nome", "e_mail"})
public class ClienteDto {

	@ApiParam(type = "string", name = "nome", example = "Nome do cliente")
	@JsonProperty("nome")
	private String nome;

	@ApiParam(type = "string", name = "e_mail", example = "E-mail do cliente: gutodarbem@gmail.com")
	@JsonProperty("e_mail")
	private String email;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


}
