package br.com.luizalabs.desafio.controller;

import java.util.ArrayList;
import java.util.List;

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

import br.com.luizalabs.desafio.dto.AdicionarProduto;
import br.com.luizalabs.desafio.dto.MensagemDto;
import br.com.luizalabs.desafio.dto.Paginacao;
import br.com.luizalabs.desafio.dto.ProdutoDto;
import br.com.luizalabs.desafio.dto.ProdutoPaginacaoDto;
import br.com.luizalabs.desafio.service.ProdutoFavoritoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Gerenciar Produtos favorito do Cliente")
@RequestMapping(value = "/produto")
@Controller
public class ProdutoController {

	private static final Logger LOG = LoggerFactory.getLogger(ProdutoController.class);

	private static final int BAD_REQUEST = 400;
	
	@Autowired
	private ProdutoFavoritoService service;

	@ApiOperation(value = "Recuperar lista de produtos favoritos do Cliente", nickname = "apagarCliente"
			, notes = "Recuperar a lista de produtos favorito do cliente através do <b>'e-mail'</b>")
	@ApiResponses(value = { 
			@ApiResponse(code = 206, message = "Partial Content", response = ProdutoPaginacaoDto.class),
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
	public ResponseEntity<ProdutoPaginacaoDto> listarProdutosPorCliente(
			@ApiParam(value = "Identificador do cliente", example = "123", type = "long", name = "id") 
			@PathVariable(name = "id", required = true) final Long id,
			
			@ApiParam(value = "Página", required = false, allowEmptyValue = true, example = "1", type = "int", name = "page") 
			@RequestParam(required = false, name = "page", defaultValue = "1") final Integer page,
			
			@ApiParam(value = "Tamanho da página", required = false, allowEmptyValue = true, example = "100", type = "int", name = "size")
			@RequestParam(required = false, name = "size", defaultValue = "100") final Integer size) {

		ProdutoPaginacaoDto paginacaoDto =  service.getProdutoFavorito(id, page, size);

		return new ResponseEntity<ProdutoPaginacaoDto>(paginacaoDto, HttpStatus.OK);
	}

	@ApiOperation(value = "Adicionar produto favorito", nickname = "acidionarProdutoFavorito", notes = "Adicionar produtos favorito")
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void acidionarProdutoFavorito(
			@ApiParam(value = "Identificador do cliente", example = "123", type = "long", name = "id")
			@PathVariable(name = "id", required = true) final Long id,
			@ApiParam(value = "Identificador dos produtos", required = true, allowEmptyValue = false, example = "", type = "AdicionarProduto", name = "ids") 
			@RequestBody final AdicionarProduto adicionarProduto) {

		service.adicionarProdutoFavorito(id, adicionarProduto.getIds());

	}

	@ApiOperation(value = "Remover produto favorito", nickname = "acidionarProdutoFavorito", notes = "Remover produtos favorito")
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removerProdutoFavorito(
			@ApiParam(value = "Identificador do cliente", example = "123", type = "long", name = "id") 
			@PathVariable(name = "id", required = true) final Long id,
			@ApiParam(value = "Identificador dos produtos", type = "AdicionarProduto", name = "ids") 
			@RequestBody(required = true) final AdicionarProduto adicionarProduto

	) {

		service.removerProdutoFavorito(id, adicionarProduto.getIds());

	}

}
