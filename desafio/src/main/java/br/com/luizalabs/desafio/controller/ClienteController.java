package br.com.luizalabs.desafio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.luizalabs.desafio.domain.MensagemEnum;
import br.com.luizalabs.desafio.dto.ClienteDto;
import br.com.luizalabs.desafio.dto.ClientePaginacaoDto;
import br.com.luizalabs.desafio.dto.MensagemDto;
import br.com.luizalabs.desafio.dto.RetornoIdDto;
import br.com.luizalabs.desafio.exception.CustomException;
import br.com.luizalabs.desafio.exception.InternalErrorException;
import br.com.luizalabs.desafio.service.ClienteService;
import br.com.luizalabs.desafio.util.MensagemUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Gerenciar Cliente")
@RequestMapping(value = "/cliente")
@Controller
public class ClienteController {

	private static final Logger LOG = LoggerFactory.getLogger(ClienteController.class);

	private static final int PARTIAL_CONTENT = 206;

	private static final int BAD_REQUEST = 400;
	
	@Autowired
	private MensagemUtil mensagem;
	
	@Autowired
	private ClienteService clienteService;

	@ApiOperation(value = "Incluir Cliente", nickname = "incluirCliente", notes = "Nesse método é possível incluir o cliente"
			+ "<br> Use o <b>e-mail</b> como a chave de busca, ele é unico em nossa base de dados."
			, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE )
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<RetornoIdDto> incluirCliente(
			@ApiParam(value = "Token de Autorização", required = true, example = "Bearer d5298030-fb34-4cae-a6cd-7b26abae9e42", type = "string", name = "Authorization")
			@RequestHeader("Authorization") final String authorization,
			
			@ApiParam(value = "Dados do Cliente", required = true) 
			@RequestBody(required = true) final ClienteDto cliente) {
		
		try {
			RetornoIdDto id =  clienteService.inserirCliente(cliente);
			
			
			return new ResponseEntity<RetornoIdDto>(id, HttpStatus.CREATED);
		}catch (CustomException e) {
			throw e;
		}catch (Throwable e) {
			LOG.error("Erro ao tentar incluir cliente ({}). Erro: {}",cliente, e);
			throw new InternalErrorException(getMensagemDto(MensagemEnum.ERRO_INEXPERADO));
		}
	}

	@ApiOperation(value = "Atualizar Cliente", nickname = "atualizarCliente", notes = "Nesse método é possível atualizar todos os atributos "
			+ "do cliente. <br> <n> Obs.: Campos passados como vazio, nulo ou somente com espaços serão desconsiderado.</n>"
			, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, method = RequestMethod.PUT)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void atualizarCliente(
			@ApiParam(value = "Token de Autorização", required = true, example = "Bearer d5298030-fb34-4cae-a6cd-7b26abae9e42", type = "string", name = "Authorization")
			@RequestHeader("Authorization") final String authorization,
			
			@ApiParam(value = "Dados do Cliente", required = true) 
			@RequestBody(required = true) final ClienteDto cliente) {
		try {
			clienteService.updateCliente(cliente);
		}catch (CustomException e) {
			throw e;
		}catch (Throwable e) {
			LOG.error("Erro ao tentar atualizar cliente ({}). Erro: {}",cliente, e);
			throw new InternalErrorException(getMensagemDto(MensagemEnum.ERRO_INEXPERADO));
		}

	}

	@ApiOperation(value = "Listar Todos Clientes", nickname = "listarCliente", notes = "Recuperar uma lista de clientes paginados por até 100 registros. "
			+ "A customização da quantidade de registros pode ser feita informando o parâmetro <b>'size'</b>. A paginação é feita através do parâmetro <b>'page'</b>"
			, response = ClientePaginacaoDto.class)
	@ApiResponses(value = { 
			@ApiResponse(code = PARTIAL_CONTENT, message = "Partial Content", response = ClientePaginacaoDto.class),
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, method = RequestMethod.GET)
	public ResponseEntity<ClientePaginacaoDto> listarCliente(
			@ApiParam(value = "Token de Autorização", required = true, example = "Bearer d5298030-fb34-4cae-a6cd-7b26abae9e42", type = "string", name = "Authorization")
			@RequestHeader("Authorization") final String authorization,
			
			@ApiParam(value = "Página", required = false, allowEmptyValue = true, example = "1", type = "int", name = "page") 
			@RequestParam(required = false, name = "page", defaultValue = "1") final Integer page,
			
			@ApiParam(value = "Tamanho da página", required = false, allowEmptyValue = true, example = "100", type = "int", name = "size")
			@RequestParam(required = false, name = "size", defaultValue = "100") final Integer size) {

		try {
			ClientePaginacaoDto clientePaginacao = clienteService.findAll(page, size);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(clientePaginacao.getMeta().isNextPage()) {
				 httpStatus = HttpStatus.PARTIAL_CONTENT;
			}
		
			return new ResponseEntity<ClientePaginacaoDto>(clientePaginacao, httpStatus);
		}catch (CustomException e) {
			throw e;
		}catch (Throwable e) {
			LOG.error("Erro ao tentar recuperar cliente (page = {}, size = {}). Erro: {}",page, size, e);
			throw new InternalErrorException(getMensagemDto(MensagemEnum.ERRO_INEXPERADO));
		}
	}
	
	@ApiOperation(value = "Buscar Clientes por E-mail", nickname = "buscaCliente", notes = "Recuperar o cliente através do <b>'e-mail'<\b>"
			+ " ou da <b>'chave'</b>",response = ClienteDto.class)
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(path = "/{id-email}",produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, method = RequestMethod.GET)
	public ResponseEntity<ClienteDto> buscaCliente(
			@ApiParam(value = "Token de Autorização", required = true, example = "Bearer d5298030-fb34-4cae-a6cd-7b26abae9e42", type = "string", name = "Authorization")
			@RequestHeader("Authorization") final String authorization,
			
			@ApiParam(value = "Identificador do cliente", example = "123", type = "string", name = "id-email")
			@PathVariable(name = "id-email", required = true) final String id){

		try {
			ClienteDto cliente = clienteService.getCliente(id);
	
			return new ResponseEntity<ClienteDto>(cliente, HttpStatus.OK);
		}catch (CustomException e) {
			throw e;
		}catch (Throwable e) {
			LOG.error("Erro ao tentar recuperar cliente (email ou id = {}). Erro: {}", id, e);
			throw new InternalErrorException(getMensagemDto(MensagemEnum.ERRO_INEXPERADO));
		}
	}

	@ApiOperation(value = "Apagar Cliente", nickname = "apagarCliente", notes = "Apagar cliente através do <b>'e-mail'<\b>")
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void apagarCliente(
			@ApiParam(value = "Token de Autorização", required = true, example = "Bearer d5298030-fb34-4cae-a6cd-7b26abae9e42", type = "string", name = "Authorization")
			@RequestHeader("Authorization") final String authorization,
			
			@ApiParam(value = "Identificador do cliente", example = "123", type = "long", name = "id") 
			@PathVariable(name = "id", required = true) final Long id) {

		try {
			clienteService.apagarCliente(id);
		}catch (CustomException e) {
			throw e;
		}catch (Throwable e) {
			LOG.error("Erro ao tentar excluír cliente. {}",e);
			throw new InternalErrorException(getMensagemDto(MensagemEnum.ERRO_INEXPERADO));
		}

	}
	
	private MensagemDto  getMensagemDto(MensagemEnum mensagemEnum) {
		
		MensagemDto mensagemDto = new MensagemDto();
		mensagemDto.setCodigo(mensagemEnum.getId());
		mensagemDto.setMensagem(mensagem.getMessageDesc(mensagemEnum.getMensagem()));
		
		return mensagemDto;
	}


}
