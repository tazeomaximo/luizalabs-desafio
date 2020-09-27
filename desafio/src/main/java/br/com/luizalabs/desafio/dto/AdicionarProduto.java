package br.com.luizalabs.desafio.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel("AdicionarProduto")
public class AdicionarProduto {

	@ApiParam(type = "array", name = "ids", example = "Lista de código dos produtos")
	@JsonProperty("ids")
	private Set<String> ids;

	public Set<String> getIds() {
		return ids;
	}

	public void setIds(Set<String> ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "AdicionarProduto [ids=" + ids + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ids == null) ? 0 : ids.hashCode());
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
		AdicionarProduto other = (AdicionarProduto) obj;
		if (ids == null) {
			if (other.ids != null)
				return false;
		} else if (!ids.equals(other.ids))
			return false;
		return true;
	}

}
