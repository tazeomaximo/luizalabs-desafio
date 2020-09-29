package br.com.luizalabs.desafio.domain;

public enum MensagemEnum {
	
	ERRO_CAMPO_OBRIGATORIO("1", "error.required.field"),
	ERRO_INEXPERADO("2", "error.unexpected"),
	ERRO_EMAIL_JA_CADASTRADO("3", "error.email.registered"),
	ERRO_TAMANHO_PAGINA_NAO_PERMITIDO("4", "error.size"),
	ERRO_PAGINA_NAO_ENCONTRADA("5", "error.page"),
	ERRO_REGISTRO_NAO_ENCONTRADO("6", "error.record.not.found"),
	ERRO_DADOS_ATUALIZAR_NAO_INFORMADO("7", "error.data.update.not.found"),
	ERRO_SEM_OBJETO("8", "error.no.object.found"),
	ERRO_CAMPO_NAO_PERMITIDO("9", "error.field.not.allowed"),;
	
	
	private MensagemEnum(String id, String mensagem) {
		this.id = id;
		this.mensagem = mensagem;
	}

	private String id;
	
	private String mensagem;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
