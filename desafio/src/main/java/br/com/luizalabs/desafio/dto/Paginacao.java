package br.com.luizalabs.desafio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;

@ApiModel("Paginacao")
@JsonPropertyOrder(value = { "page_number", "page_size" })
public class Paginacao {

	@JsonProperty("page_number")
	private Integer pageNumber;

	@JsonProperty("page_size")
	private Integer pagerSize;
	
	public Paginacao() {
	}
	
	public Paginacao(Integer page, Integer size) {
		this.pageNumber = page;
		this.pagerSize = size;
	}


	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPagerSize() {
		return pagerSize;
	}

	public void setPagerSize(Integer pagerSize) {
		this.pagerSize = pagerSize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pageNumber == null) ? 0 : pageNumber.hashCode());
		result = prime * result + ((pagerSize == null) ? 0 : pagerSize.hashCode());
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
		Paginacao other = (Paginacao) obj;
		if (pageNumber == null) {
			if (other.pageNumber != null)
				return false;
		} else if (!pageNumber.equals(other.pageNumber))
			return false;
		if (pagerSize == null) {
			if (other.pagerSize != null)
				return false;
		} else if (!pagerSize.equals(other.pagerSize))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Page [pageNumber=" + pageNumber + ", pagerSize=" + pagerSize + "]";
	}

}
