package br.com.luizalabs.desafio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.luizalabs.desafio.dto.ClienteDto;
import br.com.luizalabs.desafio.dto.ClientePaginacao;
import br.com.luizalabs.desafio.dto.Paginacao;
import br.com.luizalabs.desafio.entity.ClienteEntity;
import br.com.luizalabs.desafio.repository.ClienteRepository;

@Service
public class ClienteService {
	
	
	private ClienteRepository clienteRepository;
	
	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	
	public ClientePaginacao findAll(Integer page, Integer size){
		
		PageRequest pr = PageRequest.of(page, size);
		
		List<ClienteEntity> clientes = clienteRepository.findAll(pr);
		
		ClientePaginacao cp = new ClientePaginacao(new Paginacao(page, size));
		
		List<ClienteDto> clienteDtos = new ArrayList<ClienteDto>();
		
		Consumer<ClienteEntity> consumer = s -> {
			ClienteDto dto = new ClienteDto();
			
			BeanUtils.copyProperties(s, dto, "produtos");
			
			clienteDtos.add(dto);
		};
		
		
		
		clientes.forEach(consumer);
		
		cp.setClientes(clienteDtos);
		
		return cp;
	}
	

}
