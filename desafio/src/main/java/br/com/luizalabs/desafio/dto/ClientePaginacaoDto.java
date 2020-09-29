package br.com.luizalabs.desafio.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;

@ApiModel("ClientePaginacao")
public class ClientePaginacaoDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("meta")
	private PaginacaoDto meta;
	
	@JsonProperty("clients")
	private List<ClienteDto> clientes;
	
	public ClientePaginacaoDto(PaginacaoDto paginacao){
		this.meta = paginacao;
	}
	
	public ClientePaginacaoDto(){
	}
	
	public PaginacaoDto getMeta() {
		return meta;
	}

	public void setMeta(PaginacaoDto meta) {
		this.meta = meta;
	}

	public List<ClienteDto> getClientes() {
		return clientes;
	}

	public void setClientes(List<ClienteDto> clientes) {
		this.clientes = clientes;
	}

}
