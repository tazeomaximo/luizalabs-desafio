package br.com.luizalabs.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.luizalabs.desafio.dto.MensagemDto;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundRequestException extends CustomException {

	private static final long serialVersionUID = -1904573938307619037L;

	private MensagemDto mensagemErro = null;

	public NotFoundRequestException() {
	}

	public NotFoundRequestException(final MensagemDto mensagemErro) {
		this.mensagemErro = mensagemErro;
	}

	public MensagemDto getMensagemErro() {
		return this.mensagemErro;
	}

	public void setMensagemErro(final MensagemDto mensagemErro) {
		this.mensagemErro = mensagemErro;

	}

}
