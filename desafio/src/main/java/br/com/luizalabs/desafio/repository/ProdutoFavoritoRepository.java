package br.com.luizalabs.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.luizalabs.desafio.entity.ProdutoFavoritoEntity;
import br.com.luizalabs.desafio.entity.ProdutoFavoritoId;

@Repository
public interface ProdutoFavoritoRepository extends CrudRepository<ProdutoFavoritoEntity, ProdutoFavoritoId> {

	@Query("select pf from #{#entityName} pf where pf.id.idCliente = ?1")
	List<ProdutoFavoritoEntity> findByIdByidCliente(Long id);
}
