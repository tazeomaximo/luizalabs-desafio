package br.com.luizalabs.desafio.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.luizalabs.desafio.domain.MensagemEnum;
import br.com.luizalabs.desafio.dto.MensagemDto;
import br.com.luizalabs.desafio.exception.BadRequestException;
import br.com.luizalabs.desafio.exception.InternalErrorException;
import br.com.luizalabs.desafio.exception.NotFoundRequestException;
import br.com.luizalabs.desafio.util.MensagemUtil;

@ControllerAdvice
@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);
	
	@Autowired
	private MensagemUtil mensagem;

	@ExceptionHandler(value = BadRequestException.class)
	public final ResponseEntity<Object> handleBadRequestException(final BadRequestException ex,
	    final WebRequest request) {

		LOG.debug(ex.getMensagemErro().toString());
		
		return new ResponseEntity<>(ex.getMensagemErro(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = InternalErrorException.class)
	public final ResponseEntity<Object> handleInternalErrorException(final InternalErrorException ex,
	    final WebRequest request) {

		LOG.error(ex.getMensagemErro().toString());
		
		return new ResponseEntity<>(ex.getMensagemErro(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = NotFoundRequestException.class)
	public final ResponseEntity<Object> handleInternalErrorException(final NotFoundRequestException ex,
	    final WebRequest request) {

		LOG.error(ex.getMensagemErro().toString());
		
		return new ResponseEntity<>(ex.getMensagemErro(), HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		LOG.error("Erro Inesperado: {}", ex);
		
		if(ex instanceof HttpRequestMethodNotSupportedException) {
			return super.handleExceptionInternal(ex, body, headers, status, request);
		}
			
		MensagemDto mensagemErro = new MensagemDto();
		mensagemErro.setCodigo(MensagemEnum.ERRO_INEXPERADO.getId());
		mensagemErro.setMensagem(mensagem.getMessageDesc(MensagemEnum.ERRO_INEXPERADO.getMensagem()));
		return new ResponseEntity<>(mensagemErro, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOG.error("Erro Inesperado: {}", ex);
		MensagemDto mensagemErro = new MensagemDto();
		mensagemErro.setCodigo(MensagemEnum.ERRO_SEM_OBJETO.getId());
		mensagemErro.setMensagem(mensagem.getMessageDesc(MensagemEnum.ERRO_SEM_OBJETO.getMensagem()));
		return new ResponseEntity<>(mensagemErro, HttpStatus.BAD_REQUEST);
	}

	
}
