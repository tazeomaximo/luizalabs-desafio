package br.com.luizalabs.desafio.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import br.com.luizalabs.desafio.dto.Paginacao;
import br.com.luizalabs.desafio.dto.ProdutoDto;
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

	@ApiOperation(value = "Incluir Cliente", nickname = "incluirCliente", notes = "Nesse método é possível incluir o cliente"
			+ "<br> Use o <b>e-mail</b> como a chave de busca, ele é unico em nossa base de dados."
			, consumes = MediaType.APPLICATION_JSON_VALUE )
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void incluirCliente(
			@ApiParam(value = "Dados do Cliente", required = true) 
			@RequestBody(required = true) final ClienteDto cliente) {

		LOG.info(cliente.toString());

	}

	@ApiOperation(value = "Atualizar Cliente", nickname = "atualizarCliente", notes = "Nesse método é possível atualizar todos os atributos "
			+ "do cliente."
			, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/{e-mail}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.PUT)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void atualizarCliente(
			@ApiParam(value = "Identificador do cliente", example = "gutodarbem@gmail.com", type = "string", name = "e-mail") 
			@PathVariable(name = "e-mail", required = true) final String email,
			@ApiParam(value = "Dados do Cliente", required = true) 
			@RequestBody(required = true) final ClienteDto cliente) {

		LOG.info(cliente.toString());

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

		List<ClienteDto> clientes = new ArrayList<ClienteDto>();
		ClientePaginacao cp = new ClientePaginacao();

		for (Long j = 0L; j < 100; j++) {

			ClienteDto cliente = new ClienteDto();
			cliente.setEmail("teste@gmail.com " + j);
			cliente.setNome("Guto Darbem " + j);

			List<ProdutoDto> produtos = new ArrayList<ProdutoDto>();

			for (Long i = 0L; i <= j; i++) {
				ProdutoDto produto = new ProdutoDto();

				produto.setId(i.toString());
				produto.setImagem("URL_IMAGEM");
				produto.setPreco(1.6 + i);
				produto.setReviewScore(12.5 + i);
				produto.setTitulo("Titulo: " + i);

				produtos.add(produto);
			}

			//cliente.setProdutos(produtos);
			clientes.add(cliente);
		}

		Paginacao p = new Paginacao();
		p.setPageNumber(1);
		p.setPagerSize(10);

		cp.setMeta(p);
		cp.setClientes(clientes);

		LOG.info("Pagina: {}, Tamanho: {}", page, size);

		return new ResponseEntity<ClientePaginacao>(cp, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Buscar Clientes por E-mail", nickname = "buscaClientePorEmail", notes = "Recuperar o cliente através do <b>'e-mail'<\b>"
			+ "através do <b>'e-mail'</b>",response = ClienteDto.class)
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/{e-mail}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
	public ResponseEntity<ClienteDto> buscaClientePorEmail(
			@ApiParam(value = "Identificador do cliente", example = "gutodarbem@gmail.com", type = "string", name = "e-mail") 
			@PathVariable(name = "e-mail", required = true) final String email) {


			ClienteDto cliente = new ClienteDto();
			cliente.setEmail("teste@gmail.com ");
			cliente.setNome("Guto Darbem");

		return new ResponseEntity<ClienteDto>(cliente, HttpStatus.OK);
	}

	@ApiOperation(value = "Apagar Cliente", nickname = "apagarCliente", notes = "Apagar cliente através do <b>'e-mail'<\b>")
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/{e-mail}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void apagarCliente(
			@ApiParam(value = "Identificador do cliente", example = "gutodarbem@gmail.com", type = "string", name = "e-mail") 
			@PathVariable(name = "e-mail", required = true) final String email) {

		LOG.info("Pagina: {}, Tamanho: {}");

	}


}
