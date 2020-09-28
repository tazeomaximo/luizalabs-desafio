package br.com.luizalabs.desafio.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdutoPaginacaoDto {

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

	@Override
	public String toString() {
		return "ProdutoPaginacaoDto [meta=" + meta + ", produtos=" + produtos + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((meta == null) ? 0 : meta.hashCode());
		result = prime * result + ((produtos == null) ? 0 : produtos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoPaginacaoDto other = (ProdutoPaginacaoDto) obj;
		if (meta == null) {
			if (other.meta != null)
				return false;
		} else if (!meta.equals(other.meta))
			return false;
		if (produtos == null) {
			if (other.produtos != null)
				return false;
		} else if (!produtos.equals(other.produtos))
			return false;
		return true;
	}

}
