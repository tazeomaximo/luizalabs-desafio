package br.com.luizalabs.desafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.luizalabs.desafio.entity.User;

@Service
public class UserService implements UserDetailsService{
	
	@Value("${access.api.password}")
	private String password;

	@Value("${access.api.username}")
	private String userName;
	
	 @Autowired
	 PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if(!username.equals(this.userName)) {
			 throw new UsernameNotFoundException(String.format("Usuário não existe!", username));
		}
		
		User user = new User();
		
		user.setPass(passwordEncoder.encode(password));
		user.setUserName(username);
		
		return user;
	}

}
