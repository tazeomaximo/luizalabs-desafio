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
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.luizalabs.desafio.dto.AdicionarProduto;
import br.com.luizalabs.desafio.dto.MensagemDto;
import br.com.luizalabs.desafio.dto.Paginacao;
import br.com.luizalabs.desafio.dto.ProdutoDto;
import br.com.luizalabs.desafio.dto.ProdutoPaginacaoDto;
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

	@ApiOperation(value = "Recuperar lista de produtos favoritos do Cliente", nickname = "apagarCliente"
			, notes = "Recuperar a lista de produtos favorito do cliente através do <b>'e-mail'</b>")
	@ApiResponses(value = { 
			@ApiResponse(code = 206, message = "Partial Content", response = ProdutoPaginacaoDto.class),
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/{e-mail}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
	public ResponseEntity<ProdutoPaginacaoDto> listarProdutosPorCliente(
			@ApiParam(value = "Identificador do cliente", example = "gutodarbem@gmail.com", type = "string", name = "e-mail") 
			@PathVariable(name = "e-mail", required = true) final String email) {

		LOG.info("Pagina: {}, Tamanho: {}", email);
		
		List<ProdutoDto> produtos = new ArrayList<ProdutoDto>();

		for (Long i = 0L; i <= 5; i++) {
			ProdutoDto produto = new ProdutoDto();

			produto.setId(i.toString());
			produto.setImagem("URL_IMAGEM");
			produto.setPreco(1.6 + i);
			produto.setReviewScore(12.5 + i);
			produto.setTitulo("Titulo: " + i);

			produtos.add(produto);
		}
		
		ProdutoPaginacaoDto produtoPaginacaoDto = new ProdutoPaginacaoDto();
		
		Paginacao p = new Paginacao();
		p.setPageNumber(1);
		p.setPagerSize(10);
		
		produtoPaginacaoDto.setMeta(p);
		produtoPaginacaoDto.setProdutos(produtos);

		return new ResponseEntity<ProdutoPaginacaoDto>(produtoPaginacaoDto, HttpStatus.OK);
	}

	@ApiOperation(value = "Adicionar produto favorito", nickname = "acidionarProdutoFavorito", notes = "Adicionar produtos favorito")
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/{e-mail}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void acidionarProdutoFavorito(
			@ApiParam(value = "Identificador do cliente", example = "gutodarbem@gmail.com", type = "string", name = "e-mail")
			@PathVariable(name = "e-mail", required = true) final String email,
			@ApiParam(value = "Identificador dos produtos", required = true, allowEmptyValue = false, example = "", type = "AdicionarProduto", name = "ids") 
			@RequestBody final AdicionarProduto adicionarProduto

	) {

		LOG.info("IDs: {}", adicionarProduto.getIds());

	}

	@ApiOperation(value = "Remover produto favorito", nickname = "acidionarProdutoFavorito", notes = "Remover produtos favorito")
	@ApiResponses(value = { 
			@ApiResponse(code = BAD_REQUEST, message = "", response = MensagemDto.class) })
	@RequestMapping(value = "/{e-mail}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void removerProdutoFavorito(
			@ApiParam(value = "Identificador do cliente", example = "gutodarbem@gmail.com", type = "string", name = "e-mail") 
			@PathVariable(name = "e-mail", required = true) final String email,
			@ApiParam(value = "Identificador dos produtos", type = "AdicionarProduto", name = "ids") 
			@RequestBody(required = true) final AdicionarProduto adicionarProduto

	) {

		LOG.info("IDs: {}", adicionarProduto.getIds());

	}

}
