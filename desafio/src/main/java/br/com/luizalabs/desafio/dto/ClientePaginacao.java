package br.com.luizalabs.desafio.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel("ClientePaginacao")
public class ClientePaginacao implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("meta")
	private Paginacao meta;
	
	@JsonProperty("clients")
	private List<ClienteDto> clientes;
	
	public ClientePaginacao(Paginacao paginacao){
		this.meta = paginacao;
	}
	
	public ClientePaginacao(){
	}
	
	public Paginacao getMeta() {
		return meta;
	}

	public void setMeta(Paginacao meta) {
		this.meta = meta;
	}

	public List<ClienteDto> getClientes() {
		return clientes;
	}

	public void setClientes(List<ClienteDto> clientes) {
		this.clientes = clientes;
	}

}
