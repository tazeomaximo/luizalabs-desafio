package br.com.luizalabs.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.luizalabs.desafio.dto.MensagemDto;

/**
 * @author Guto
 *
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalErrorException extends CustomException {

	private static final long serialVersionUID = -1904573938307619037L;

	private MensagemDto mensagemErro = null;

	public InternalErrorException() {
	}

	public InternalErrorException(final MensagemDto mensagemErro) {
		this.mensagemErro = mensagemErro;
	}

	public MensagemDto getMensagemErro() {
		return this.mensagemErro;
	}

	public void setMensagemErro(final MensagemDto mensagemErro) {
		this.mensagemErro = mensagemErro;

	}	
	
}
