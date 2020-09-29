package br.com.luizalabs.desafio.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel("RetornoId")
public class RetornoIdDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9050156412850542004L;
	
	@ApiParam(name = "id", type = "int")
	@JsonProperty("id")
	private Object id;

	public RetornoIdDto(Object id) {
		this.id = id;
	}
	
	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		RetornoIdDto other = (RetornoIdDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RetornoId [id=" + id + "]";
	}
	
	
}
