package br.com.luizalabs.desafio.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;

@ApiModel("ProdutoPaginacao")
@JsonPropertyOrder(value = { "meta"})
public class ProdutoPaginacaoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7272405404302749434L;

	@JsonProperty("meta")
	private Paginacao meta;

	@JsonProperty("products")
	private List<ProdutoDto> produtos;
	
	public ProdutoPaginacaoDto(Paginacao paginacao) {
		this.meta = paginacao;
	}
	
	public ProdutoPaginacaoDto() {
	}

	public Paginacao getMeta() {
		return meta;
	}

	public void setMeta(Paginacao meta) {
		this.meta = meta;
	}

	public List<ProdutoDto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoDto> produtos) {
		this.produtos = produtos;
	}

}
