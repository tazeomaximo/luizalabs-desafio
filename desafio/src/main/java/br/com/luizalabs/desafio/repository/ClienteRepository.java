package br.com.luizalabs.desafio.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.luizalabs.desafio.entity.ClienteEntity;

@Repository
public interface ClienteRepository extends CrudRepository<ClienteEntity, Long> {
	
	ClienteEntity findByEmail(String email);
	
	List<ClienteEntity> findAll(Pageable pageable);
}
