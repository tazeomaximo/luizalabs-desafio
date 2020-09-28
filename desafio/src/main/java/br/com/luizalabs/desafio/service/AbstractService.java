package br.com.luizalabs.desafio.service;

import org.springframework.data.domain.PageImpl;

import br.com.luizalabs.desafio.domain.MensagemEnum;
import br.com.luizalabs.desafio.dto.MensagemDto;
import br.com.luizalabs.desafio.dto.Paginacao;
import br.com.luizalabs.desafio.exception.BadRequestException;
import br.com.luizalabs.desafio.exception.NotFoundRequestException;
import br.com.luizalabs.desafio.util.MensagemUtil;

public class AbstractService {
	
	private MensagemUtil mensagemUtil;

	public AbstractService(MensagemUtil  mensagemUtil){
		this.mensagemUtil = mensagemUtil;
	}
	
	
	public AbstractService() {
	}

	protected void validarPaginacao(Integer page, Integer size) {
		
		if(page < 1) {
			throw new BadRequestException(getMensagemDto(MensagemEnum.ERRO_PAGINA, page));
		}
		
		if(size > 100 || size < 1) {
			throw new BadRequestException(getMensagemDto(MensagemEnum.ERRO_TAMANHO_PAGINA));
		}
	}
	
	protected Paginacao iniciaPaginacao(Integer page, Integer size, PageImpl<?> pageImp) {
		if(page > pageImp.getTotalPages()) {
			throw new BadRequestException(getMensagemDto(MensagemEnum.ERRO_PAGINA, page));
		}
		
		boolean nextPage = isNextPage(pageImp, page);
		
		Paginacao p = new Paginacao(page, size, pageImp.getTotalPages(),pageImp.getTotalElements(), nextPage);
		return p;
	}
	
	protected boolean isNextPage(PageImpl<?> pageImpl, Integer page) {
		return pageImpl.getTotalPages() > page;
	}

	protected MensagemDto getMensagemDto(MensagemEnum mensagemEnum) {
		return getMensagemDto(mensagemEnum, null);
	}

	protected MensagemDto getMensagemDto(MensagemEnum mensagemEnum, Object...param ){

		MensagemDto mensagemDto = new MensagemDto();
		mensagemDto.setCodigo(mensagemEnum.getId());
		mensagemDto.setMensagem(mensagemUtil.getMessageDesc(mensagemEnum.getMensagem(), param));

		return mensagemDto;
	}
	
	protected NotFoundRequestException registroNaoEncontrado(Object id) {
		return new NotFoundRequestException(
				getMensagemDto(MensagemEnum.ERRO_REGISTRO_NAO_ENCONTRADO,id));
	}

}
