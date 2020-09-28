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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.luizalabs.desafio.dto.ClienteDto;
import br.com.luizalabs.desafio.dto.ClientePaginacao;
import br.com.luizalabs.desafio.dto.MensagemDto;
import br.com.luizalabs.desafio.dto.RetornoId;
import br.com.luizalabs.desafio.service.ClienteService;
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

	private static final int BAD_REQUEST = 400;
	
	@Autowired
	private ClienteService clienteService;

	@ApiOperation(value = "Incluir Cliente", nickname = "incluirCliente", notes = "Nesse método é possível incluir o cliente"
			+ "<br> Use o <b>e-mail</b> como a chave de busca, ele é unico em nossa base de dados."
			, consumes = MediaType.APPLICATION_JSON_VALUE )
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<RetornoId> incluirCliente(
			@ApiParam(value = "Dados do Cliente", required = true) 
			@RequestBody(required = true) final ClienteDto cliente) {

		RetornoId id =  clienteService.inserirCliente(cliente);
		
		
		return new ResponseEntity<RetornoId>(id, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Atualizar Cliente", nickname = "atualizarCliente", notes = "Nesse método é possível atualizar todos os atributos "
			+ "do cliente."
			, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PUT)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void atualizarCliente(
			@ApiParam(value = "Dados do Cliente", required = true) 
			@RequestBody(required = true) final ClienteDto cliente) {

		clienteService.updateCliente(cliente);

	}

	@ApiOperation(value = "Listar Todos Clientes", nickname = "listarCliente", notes = "Recuperar uma lista de clientes paginados por até 100 registros. "
			+ "A customização da quantidade de registros pode ser feita informando o parâmetro <b>'size'</b>. A paginação é feita através do parâmetro <b>'page'</b>"
			, response = ClientePaginacao.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 206, message = "Partial Content", response = ClientePaginacao.class),
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
	public ResponseEntity<ClientePaginacao> listarCliente(
			@ApiParam(value = "Página", required = false, allowEmptyValue = true, example = "1", type = "int", name = "page") 
			@RequestParam(required = false, name = "page", defaultValue = "1") final Integer page,
			
			@ApiParam(value = "Tamanho da página", required = false, allowEmptyValue = true, example = "100", type = "int", name = "size")
			@RequestParam(required = false, name = "size", defaultValue = "100") final Integer size) {

		
		ClientePaginacao clientePaginacao = clienteService.findAll(page, size);
		
		return new ResponseEntity<ClientePaginacao>(clientePaginacao, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Buscar Clientes por E-mail", nickname = "buscaCliente", notes = "Recuperar o cliente através do <b>'e-mail'<\b>"
			+ " ou da <b>'chave'</b>",response = ClienteDto.class)
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, params = {"e-mail", "id"})
	public ResponseEntity<ClienteDto> buscaCliente(
			@ApiParam(value = "Identificador do cliente", example = "gutodarbem@gmail.com", type = "string", name = "e-mail")
			@RequestParam(required = false, name = "e-mail") final String email,
			
			@ApiParam(value = "Identificador do cliente", example = "123", type = "long", name = "id")
			@RequestParam(name = "id", required = true) final Long id){


		ClienteDto cliente = clienteService.getCliente(email,id);

		return new ResponseEntity<ClienteDto>(cliente, HttpStatus.OK);
	}

	@ApiOperation(value = "Apagar Cliente", nickname = "apagarCliente", notes = "Apagar cliente através do <b>'e-mail'<\b>")
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void apagarCliente(
			@ApiParam(value = "Identificador do cliente", example = "123", type = "long", name = "id") 
			@PathVariable(name = "id", required = true) final Long id) {

		clienteService.apagarCliente(id);

	}


}
