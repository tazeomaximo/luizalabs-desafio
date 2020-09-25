package br.com.luizalabs.desafio.dto;

import java.util.List;

public class ClientePaginacao extends Paginacao {
	
	private List<ClienteDto> clientes;

	public List<ClienteDto> getClientes() {
		return clientes;
	}

	public void setClientes(List<ClienteDto> clientes) {
		this.clientes = clientes;
	}
	

}
