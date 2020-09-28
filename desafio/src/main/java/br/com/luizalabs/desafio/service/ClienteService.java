package br.com.luizalabs.desafio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.luizalabs.desafio.dto.ClienteDto;
import br.com.luizalabs.desafio.dto.ClientePaginacao;
import br.com.luizalabs.desafio.dto.Paginacao;
import br.com.luizalabs.desafio.dto.RetornoId;
import br.com.luizalabs.desafio.entity.ClienteEntity;
import br.com.luizalabs.desafio.repository.ClienteRepository;

@Service
public class ClienteService {

	private ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public void apagarCliente(Long id) {
		clienteRepository.deleteById(id);
	}

	public RetornoId inserirCliente(ClienteDto clienteDto) {
		ClienteEntity entity = new ClienteEntity();

		BeanUtils.copyProperties(clienteDto, entity);

		// TODO Adicionar a validação da entity

		clienteRepository.save(entity);
		
		
		return new RetornoId(entity.getId());

	}

	public void updateCliente(ClienteDto clienteDto) {

		Optional<ClienteEntity> optional = clienteRepository.findById(clienteDto.getId());

		ClienteEntity entity;

		if (optional.isPresent()) {
			entity = optional.get();

			if(clienteDto.getEmail() != null) {
				entity.setEmail(clienteDto.getEmail());
			}
			
			if(clienteDto.getNome() != null) {
				entity.setNome(clienteDto.getNome());
			}
			// TODO Adicionar a validação da entity

			clienteRepository.save(entity);

		}

	}

	public ClientePaginacao findAll(Integer page, Integer size) {

		ClientePaginacao cp = new ClientePaginacao(new Paginacao(page, size));

		if (page > 0) {
			page--;
		}

		// TODO colocar validação quando o cliente informar page = 0 ou uma paginacao
		// que não existe

		PageRequest pr = PageRequest.of(page, size);

		PageImpl<ClienteEntity> clientes = clienteRepository.findAll(pr);

		List<ClienteDto> clienteDtos = new ArrayList<ClienteDto>();

		Consumer<ClienteEntity> consumer = s -> {
			ClienteDto dto = new ClienteDto();

			BeanUtils.copyProperties(s, dto, "produtos");

			clienteDtos.add(dto);
		};

		clientes.getContent().forEach(consumer);

		cp.setClientes(clienteDtos);

		return cp;
	}

	public ClienteDto getCliente(String email, Long id) {
		// TODO Auto-generated method stub

		ClienteDto dto = null;
		ClienteEntity entity;
		Optional<ClienteEntity> optional = Optional.empty();

		if (id != null) {
			optional = clienteRepository.findById(id);
			
		} else if (!StringUtils.isEmpty(email)) {
			optional = clienteRepository.findByEmail(email);
		}
		if (optional.isPresent()) {
			entity = optional.get();
			BeanUtils.copyProperties(entity, dto);
		}
		
		return dto;
	}

}
