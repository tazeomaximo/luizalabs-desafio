package br.com.luizalabs.desafio.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUTO_FAVORITO")
public class ProdutoFavoritoEntity {

	@EmbeddedId
	private ProdutoFavoritoId id;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = ClienteEntity.class)
	@JoinColumn(name="id_cliente" , insertable = false, updatable = false)
	private ClienteEntity cliente;

	public ProdutoFavoritoId getId() {
		return id;
	}

	public void setId(ProdutoFavoritoId id) {
		this.id = id;
	}

	public ClienteEntity getCliente() {
		return cliente;
	}

	public void setCliente(ClienteEntity cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "ProdutoEntity [id=" + id + ", cliente=" + cliente + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
		ProdutoFavoritoEntity other = (ProdutoFavoritoEntity) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
