package br.com.luizalabs.desafio.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.luizalabs.desafio.dto.ClienteDto;
import br.com.luizalabs.desafio.dto.ClientePaginacao;
import br.com.luizalabs.desafio.dto.MensagemDto;
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
	
	public static final int SUCCESS = 200;
	private static final int BAD_REQUEST = 400;
	

	@ApiOperation(
	    value = "Salvar Cliente",
	    nickname = "salvarCliente",
	    notes = "Nesse método é possível incluir o cliente com os produtos "
	    		+ "favoritos ou atualizar-lo posteriormente")
	@ApiResponses(value = {
	    @ApiResponse(
	        code = SUCCESS,
	        message = "Success",
	       response = MensagemDto.class),
	    @ApiResponse(
	        code = BAD_REQUEST,
	        message = "Bad Request (for validation failure)",
	        response = MensagemDto.class)
	})
	@RequestMapping(
	    value = "/",
	    produces = { "application/json" },
	    method = RequestMethod.POST)
	public ResponseEntity<?> salvarCliente(
	    @ApiParam(value = "Dados do Cliente",
	        required = true) @RequestBody(required = true) final ClienteDto cliente) {
		
			LOG.info(cliente.toString());
			
			return new ResponseEntity<ClienteDto>(new ClienteDto(),HttpStatus.OK);
	}
	
	@ApiOperation(
		    value = "Listar Todos Clientes",
		    nickname = "listarCliente",
		    notes = "Listar todos os cliente paginado. Tamanho máximo por página de 100 registros. Valor padrão Página = 1 e Tamanho da página: 100")
		@ApiResponses(value = {
			@ApiResponse(
			        code = 206,
			        message = "Partial Content",
				       response = ClientePaginacao.class),
		    @ApiResponse(
		        code = BAD_REQUEST,
		        message = "Bad Request (for validation failure)",
		        response = MensagemDto.class)
		})
		@RequestMapping(
		    value = "/",
		    produces = { "application/json" },
		    method = RequestMethod.GET)
		public ResponseEntity<?> listarCliente(
		    @ApiParam(value = "Página" 
		    		  , required = false
		    		  , allowEmptyValue = true
		    		  , example = "1"
		    		  , type = "int"
		    		  , name = "page") 
		    	@RequestParam final Integer page
		    ,@ApiParam(value = "Tamanho da página" 
		    			, required = false
		    			, allowEmptyValue = true
		    			, example = "100"
			    		, type = "int"
			    		, name = "size") 
		    	@RequestParam final Integer size) {
			
				
				LOG.info("Pagina: {}, Tamanho: {}", page, size);
				
				return new ResponseEntity<ClienteDto>(new ClienteDto(),HttpStatus.OK);
		}
	
	@ApiOperation(
		    value = "Apagar Cliente",
		    nickname = "apagarCliente",
		    notes = "Apagar cliente através do identificador")
		@ApiResponses(value = {
			@ApiResponse(
			        code = 200,
			        message = "Sucesso"),
		    @ApiResponse(
		        code = BAD_REQUEST,
		        message = "Bad Request (for validation failure)",
		        response = MensagemDto.class)
		})
		@RequestMapping(
		    value = "/{e-mail}",
		    produces = { "application/json" },
		    method = RequestMethod.DELETE)
		public ResponseEntity<?> apagarCliente(
		    @ApiParam(value = "Identificador do cliente" 
		    		  , required = false
		    		  , allowEmptyValue = true
		    		  , example = "1"
		    		  , type = "int"
		    		  , name = "e-mail") 
		    	@RequestAttribute final Long identificador
		    ) {
			
				
				LOG.info("Pagina: {}, Tamanho: {}");
				
				return new ResponseEntity<ClienteDto>(new ClienteDto(),HttpStatus.OK);
		}
	
}
