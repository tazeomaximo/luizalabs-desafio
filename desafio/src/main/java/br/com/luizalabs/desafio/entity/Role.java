package br.com.luizalabs.desafio.entity;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	
	public Role(String name) {
		this.name = name;
	}
	
	@Override
	public String getAuthority() {
		return this.name;
	}

}
