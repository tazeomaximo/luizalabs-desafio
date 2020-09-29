package br.com.luizalabs.desafio.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel("Paginacao")
@JsonPropertyOrder(value = { "page_number", "page_size" })
public class PaginacaoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiParam(type = "int", name = "page_number", example = "Número da página atual")
	@JsonProperty("page_number")
	private Integer pageNumber;

	@JsonProperty("page_size")
	private Integer pageSize;

	@JsonProperty("total_records")
	private Long total;

	@JsonProperty("total_pages")
	private Integer totalPage;

	@JsonIgnore
	private boolean nextPage;

	public PaginacaoDto(Integer page, Integer size,Integer totalPage, Long total, boolean nextPage) {
		this.pageNumber = page;
		this.pageSize = size;
		this.nextPage = nextPage;
		this.total = total;
		this.totalPage = totalPage;
	}
	
	public PaginacaoDto() {
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pagerSize) {
		this.pageSize = pagerSize;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public boolean isNextPage() {
		return nextPage;
	}

	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

}
