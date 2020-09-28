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

import br.com.luizalabs.desafio.domain.MensagemEnum;
import br.com.luizalabs.desafio.dto.AdicionarProduto;
import br.com.luizalabs.desafio.dto.MensagemDto;
import br.com.luizalabs.desafio.dto.ProdutoPaginacaoDto;
import br.com.luizalabs.desafio.exception.CustomException;
import br.com.luizalabs.desafio.exception.InternalErrorException;
import br.com.luizalabs.desafio.service.ProdutoFavoritoService;
import br.com.luizalabs.desafio.util.MensagemUtil;
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
	
	private static final int PARTIAL_CONTENT = 206;
	
	@Autowired
	private MensagemUtil mensagem;
	
	@Autowired
	private ProdutoFavoritoService service;

	@ApiOperation(value = "Recuperar lista de produtos favoritos do Cliente", nickname = "apagarCliente"
			, notes = "Recuperar a lista de produtos favorito do cliente através do <b>'e-mail'</b>")
	@ApiResponses(value = { 
			@ApiResponse(code = PARTIAL_CONTENT, message = "Partial Content", response = ProdutoPaginacaoDto.class),
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
	public ResponseEntity<ProdutoPaginacaoDto> listarProdutosPorCliente(
			@ApiParam(value = "Identificador do cliente", example = "123", type = "long", name = "id") 
			@PathVariable(name = "id", required = true) final Long id,
			
			@ApiParam(value = "Página", required = false, allowEmptyValue = true, example = "1", type = "int", name = "page") 
			@RequestParam(required = false, name = "page", defaultValue = "1") final Integer page,
			
			@ApiParam(value = "Tamanho da página", required = false, allowEmptyValue = true, example = "100", type = "int", name = "size")
			@RequestParam(required = false, name = "size", defaultValue = "100") final Integer size) {

		try {
			ProdutoPaginacaoDto paginacaoDto =  service.getProdutoFavorito(id, page, size);
			
			HttpStatus httpStatus = HttpStatus.OK;
			
			if(paginacaoDto.getMeta().isNextPage()) {
				 httpStatus = HttpStatus.PARTIAL_CONTENT;
			}
	
			return new ResponseEntity<ProdutoPaginacaoDto>(paginacaoDto, httpStatus);
		}catch (CustomException e) {
			throw e;
		}catch (Throwable e) {
			LOG.error("Erro ao tentar recuperar produtos favorito. {}",e);
			throw new InternalErrorException(getMensagemDto(MensagemEnum.ERRO_INEXPERADO));
		}
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

		try {
			service.adicionarProdutoFavorito(id, adicionarProduto.getIds());
		}catch (CustomException e) {
			throw e;
		}catch (Throwable e) {
			LOG.error("Erro ao tentar adicionar produto favorito. {}",e);
			throw new InternalErrorException(getMensagemDto(MensagemEnum.ERRO_INEXPERADO));
		}

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
		
		try {
			service.removerProdutoFavorito(id, adicionarProduto.getIds());
		}catch (CustomException e) {
			throw e;
		}catch (Throwable e) {
			LOG.error("Erro ao tentar excluír produto favorito. {}",e);
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
