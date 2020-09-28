package br.com.luizalabs.desafio.repository;

import java.util.Optional;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.luizalabs.desafio.entity.ClienteEntity;

@Repository
public interface ClienteRepository extends CrudRepository<ClienteEntity, Long> {
	
	Optional<ClienteEntity> findByEmailIgnoreCase(String email);
	
	PageImpl<ClienteEntity> findAll(Pageable pageable);

	@Query("select c FROM #{#entityName} c where c.id = :id or UPPER(c.email) = UPPER(:email)")
	Optional<ClienteEntity> findByIdOrEmail(@Param("id") Long id, @Param("email") String email );
	
	@Query("select c.id  from #{#entityName} c where c.id = :id ")
	Optional<Integer> isIdClienteExiste(@Param("id") Long id);
}
