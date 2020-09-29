package br.com.luizalabs.desafio.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.luizalabs.desafio.domain.MensagemEnum;
import br.com.luizalabs.desafio.dto.ClienteDto;
import br.com.luizalabs.desafio.dto.ClientePaginacao;
import br.com.luizalabs.desafio.dto.Paginacao;
import br.com.luizalabs.desafio.dto.RetornoId;
import br.com.luizalabs.desafio.entity.ClienteEntity;
import br.com.luizalabs.desafio.exception.BadRequestException;
import br.com.luizalabs.desafio.repository.ClienteRepository;
import br.com.luizalabs.desafio.util.MensagemUtil;

@Service
public class ClienteService extends AbstractService{

	private static final String ID_OU_E_MAIL = "id ou e_mail";

	private static final String ID = "id";

	private static final String E_MAIL = "e_mail";

	private static final String NAME = "name";

	private static final Logger LOG = LoggerFactory.getLogger(ClienteService.class);

	private ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository, MensagemUtil mensagemUtil) {
		super(mensagemUtil);
		this.clienteRepository = clienteRepository;
	}

	public void apagarCliente(Long id) {
		try {
			clienteRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			LOG.warn("Cliente {} nao encontrado para exclusao.", id);
			throw registroNaoEncontrado(id);
		}
	}

	private void validarCliente(ClienteDto clienteDto) {
		
		if(!StringUtils.hasText(clienteDto.getEmail())) {
			LOG.debug("Campo e_mail nao preenchido");
			throw new BadRequestException(getMensagemDto(MensagemEnum.ERRO_CAMPO_OBRIGATORIO, E_MAIL));
		}
		clienteDto.setEmail(clienteDto.getEmail().trim());
		
		if(!StringUtils.hasText(clienteDto.getNome())) {
			LOG.debug("Campo name nao preenchido");
			throw new BadRequestException(getMensagemDto(MensagemEnum.ERRO_CAMPO_OBRIGATORIO, NAME));
		}
		clienteDto.setNome(clienteDto.getNome().trim());
	}
	
	public RetornoId inserirCliente(ClienteDto clienteDto) {
		
		validarCliente(clienteDto);
		
		ClienteEntity entity = new ClienteEntity();

		BeanUtils.copyProperties(clienteDto, entity);

		try {
			clienteRepository.save(entity);
		} catch (DataIntegrityViolationException e) {
			LOG.debug("Email duplicado", e);
			throw new BadRequestException(getMensagemDto(MensagemEnum.ERRO_EMAIL_CADASTRADO));
		}

		return new RetornoId(entity.getId());

	}

	public void updateCliente(ClienteDto clienteDto) {
		
		if(clienteDto.getId() == null || clienteDto.getId() == 0) {
			throw new BadRequestException(getMensagemDto(MensagemEnum.ERRO_CAMPO_OBRIGATORIO, ID));
		}

		ClienteEntity entity = clienteRepository.findById(clienteDto.getId())
					.orElseThrow(
							() -> registroNaoEncontrado(clienteDto.getId()));

		if(!StringUtils.hasText(clienteDto.getEmail())
				&& !StringUtils.hasText(clienteDto.getNome())) {
			
			throw new BadRequestException(getMensagemDto(MensagemEnum.ERRO_DADOS_ATUALIZAR_NAO_INFORMADO));
		}

		if (StringUtils.hasText(clienteDto.getEmail())) {
			entity.setEmail(clienteDto.getEmail().trim());
		}

		if (StringUtils.hasText(clienteDto.getNome())) {
			entity.setNome(clienteDto.getNome().trim());
		}
		
		try {
			clienteRepository.save(entity);
		} catch (DataIntegrityViolationException e) {
			LOG.debug("Email duplicado", e);
			throw new BadRequestException(getMensagemDto(MensagemEnum.ERRO_EMAIL_CADASTRADO));
		}
	}

	public ClientePaginacao findAll(Integer page, Integer size) {

		validarPaginacao(page, size);
		
		PageRequest pr = PageRequest.of(page-1, size);

		PageImpl<ClienteEntity> clientes = clienteRepository.findAll(pr);
		
		Paginacao p = iniciaPaginacao(page, size, clientes);

		List<ClienteDto> clienteDtos = new ArrayList<ClienteDto>();

		Consumer<ClienteEntity> consumer = s -> {
			ClienteDto dto = new ClienteDto();

			BeanUtils.copyProperties(s, dto, "produtos");

			clienteDtos.add(dto);
		};

		clientes.getContent().forEach(consumer);
		
		
		ClientePaginacao cp = new ClientePaginacao(p);
		
		cp.setClientes(clienteDtos);

		return cp;
	}

	public ClienteDto getCliente(String id) {

		ClienteDto dto = new ClienteDto();
		ClienteEntity entity;

		if (!StringUtils.hasText(id))
			throw new BadRequestException(getMensagemDto(MensagemEnum.ERRO_CAMPO_OBRIGATORIO, ID_OU_E_MAIL));
		
		Long idCliente = 0L;
		
		try {
			idCliente = Long.valueOf(id.trim());
		}catch(NumberFormatException e) {
			LOG.debug("A busca é pelo email = {}.", id);
		}
		
		entity = clienteRepository.findByIdOrEmail(idCliente, id.trim()).orElseThrow(
				() -> registroNaoEncontrado(id));

		BeanUtils.copyProperties(entity, dto);

		return dto;
	}

}
