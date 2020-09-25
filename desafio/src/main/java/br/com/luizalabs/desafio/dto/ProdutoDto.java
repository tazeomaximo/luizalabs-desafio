package br.com.luizalabs.desafio.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(content = Include.NON_NULL)
@ApiModel("Produto")
@JsonPropertyOrder(value = { "id", "titulo","image", "preco", "review" })
public class ProdutoDto {

	@ApiParam(type = "long", name = "id", example = "Identificador do Produto")
	@JsonProperty("id")
	private Long id;

	@ApiParam(type = "string", name = "titulo", example = "Nome do produto")
	@JsonProperty("titulo")
	private String titulo;

	@ApiParam(type = "string", name = "imagem", example = "URL da imagem do produto")
	@JsonProperty("imagem")
	private String imagem;

	@ApiParam(type = "double", name = "preco", example = "Valor do produto")
	@JsonProperty("preco")
	private Double preco;

	@ApiParam(type = "double", name = "review", example = "Média dos reviews para este produto")
	@JsonProperty("review")
	private Double reviewScore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Double getReviewScore() {
		return reviewScore;
	}

	public void setReviewScore(Double reviewScore) {
		this.reviewScore = reviewScore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((imagem == null) ? 0 : imagem.hashCode());
		result = prime * result + ((preco == null) ? 0 : preco.hashCode());
		result = prime * result + ((reviewScore == null) ? 0 : reviewScore.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
		ProdutoDto other = (ProdutoDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (imagem == null) {
			if (other.imagem != null)
				return false;
		} else if (!imagem.equals(other.imagem))
			return false;
		if (preco == null) {
			if (other.preco != null)
				return false;
		} else if (!preco.equals(other.preco))
			return false;
		if (reviewScore == null) {
			if (other.reviewScore != null)
				return false;
		} else if (!reviewScore.equals(other.reviewScore))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProdutoDto [id=" + id + ", titulo=" + titulo + ", imagem=" + imagem + ", preco=" + preco
				+ ", reviewScore=" + reviewScore + "]";
	}

}
