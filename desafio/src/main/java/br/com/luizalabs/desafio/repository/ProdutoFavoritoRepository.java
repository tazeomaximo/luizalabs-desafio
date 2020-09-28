package br.com.luizalabs.desafio.repository;

import java.util.Set;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.luizalabs.desafio.entity.ProdutoFavoritoEntity;
import br.com.luizalabs.desafio.entity.ProdutoFavoritoId;

@Repository
public interface ProdutoFavoritoRepository extends CrudRepository<ProdutoFavoritoEntity, ProdutoFavoritoId> {

	PageImpl<ProdutoFavoritoEntity> findByIdIdCliente(Long id, Pageable pageable);

	PageImpl<ProdutoFavoritoEntity> findAll(Pageable pageable);

	@Transactional
	@Modifying
	@Query("delete from #{#entityName} pf where pf.id.idCliente = ?1 and pf.id.idProduto in ?2")
	void deleteByIdClienteAndIdProduto(Long idCliente, Set<String> produtos);
}
