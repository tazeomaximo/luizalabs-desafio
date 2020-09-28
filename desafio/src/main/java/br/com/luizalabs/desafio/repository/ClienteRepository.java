package br.com.luizalabs.desafio.repository;

import java.util.Optional;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.luizalabs.desafio.entity.ClienteEntity;

@Repository
public interface ClienteRepository extends CrudRepository<ClienteEntity, Long> {
	
	Optional<ClienteEntity> findByEmail(String email);
	
	PageImpl<ClienteEntity> findAll(Pageable pageable);
}
