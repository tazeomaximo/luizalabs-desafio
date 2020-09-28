package br.com.luizalabs.desafio.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MensagemUtil{


	@Autowired
	private MessageSource messageSource;

	public String getMessageDesc(final String codigo, final Object... args) {
		return this.messageSource.getMessage(codigo, args,
		    LocaleContextHolder.getLocale());
	}

	public String getMessageDesc(final String codigo) {
		return this.getMessageDesc(codigo, null);
	}

}
