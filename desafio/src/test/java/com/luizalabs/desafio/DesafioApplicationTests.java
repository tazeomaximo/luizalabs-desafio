package com.luizalabs.desafio;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.luizalabs.desafio.DesafioApplication;
import br.com.luizalabs.desafio.dto.MensagemDto;
import br.com.luizalabs.desafio.dto.PaginacaoDto;
import br.com.luizalabs.desafio.dto.ProdutoDto;
import br.com.luizalabs.desafio.dto.ProdutoPaginacaoDto;
import br.com.luizalabs.desafio.entity.ClienteEntity;
import br.com.luizalabs.desafio.entity.ProdutoFavoritoEntity;
import br.com.luizalabs.desafio.entity.ProdutoFavoritoId;
import br.com.luizalabs.desafio.exception.BadRequestException;
import br.com.luizalabs.desafio.integration.ProdutoIntegration;
import br.com.luizalabs.desafio.repository.ClienteRepository;
import br.com.luizalabs.desafio.repository.ProdutoFavoritoRepository;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = DesafioApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DesafioApplicationTests {

	private static final String BEARER = "Bearer";

	@Value("${basic.auth.name}")
	private String user;

	@Value("${basic.auth.pass}")
	private String password;

	@Value("${access.api.username}")
	private String usernameApi;

	@Value("${access.api.password}")
	private String passwordApi;

	@LocalServerPort
	private int port;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ClienteRepository mockClienteRepository;

	@MockBean
	private ProdutoFavoritoRepository mockProdutoFavoritoRepository;

	@MockBean
	private ProdutoIntegration mockProdutoIntegration;

	private String url;

	public String getUrl() {
		if (url == null) {
			url = "http://localhost:" + port + "/";
		}
		return url;
	}

	
	@Test
	public void clientePorId_401() throws Exception {

		mockMvc.perform(get(getUrl().concat("cliente/Eurinei@gmail.com")).header(HttpHeaders.AUTHORIZATION,
				BEARER + obtainAccessToken("nouser", "nopass"))).andExpect(status().isUnauthorized());

	}

	@Test
	public void clientePorId_200() throws Exception {

		ClienteEntity entity = new ClienteEntity();
		entity.setId(-1L);
		entity.setEmail("teste@teste.com.br");

		when(mockClienteRepository.findByIdOrEmail(0L, "Eurinei@gmail.com")).thenReturn(Optional.of(entity));

		mockMvc.perform(get(getUrl().concat("cliente/Eurinei@gmail.com")).header(HttpHeaders.AUTHORIZATION,
				BEARER + obtainAccessToken(usernameApi, passwordApi))).andExpect(status().isOk())
				.andExpect(jsonPath("$.e_mail", is("teste@teste.com.br")));

	}

	@Test
	public void clientePorId_204() throws Exception {

		ClienteEntity entity = new ClienteEntity();
		entity.setId(-1L);
		entity.setEmail("teste@teste.com.br");

		when(mockClienteRepository.save(any(ClienteEntity.class))).thenReturn(entity);

		mockMvc.perform(post(getUrl().concat("cliente/"))
				.header(HttpHeaders.AUTHORIZATION, BEARER + obtainAccessToken(usernameApi, passwordApi))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content("{\"e_mail\": \"randomUserName@gmail.com\",\"name\": \"randomStreetName\"}"))
				.andExpect(status().isCreated());

	}

	@Test
	public void deleteCliente_204() throws Exception {
		

		mockMvc.perform(delete(getUrl().concat("cliente/1")).header(HttpHeaders.AUTHORIZATION,
				BEARER + obtainAccessToken(usernameApi, passwordApi))).andExpect(status().isNoContent());

		verify(mockClienteRepository).deleteById(1L);
	}

	@Test
	public void cliente_400() throws Exception {

		mockMvc.perform(post(getUrl().concat("cliente/"))
				.header(HttpHeaders.AUTHORIZATION, BEARER + obtainAccessToken(usernameApi, passwordApi))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content("{\"e_mail\": \"randomUserName@gmail.com\"}")).andExpect(status().isBadRequest());

		mockMvc.perform(post(getUrl().concat("cliente/"))
				.header(HttpHeaders.AUTHORIZATION, BEARER + obtainAccessToken(usernameApi, passwordApi))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content("{\"name\": \"randomStreetName\"}")).andExpect(status().isBadRequest());
	}

	@Test
	public void atualizarCliente_400() throws Exception {

		mockMvc.perform(put(getUrl().concat("cliente/"))
				.header(HttpHeaders.AUTHORIZATION, BEARER + obtainAccessToken(usernameApi, passwordApi))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content("{\"e_mail\": \"randomUserName@gmail.com\"}")).andExpect(status().isBadRequest());
	}

	@Test
	public void atualizarCliente_204() throws Exception {

		ClienteEntity entity = new ClienteEntity();
		entity.setId(-1L);
		entity.setEmail("1atualizar@gmail.com");
		entity.setNome("atualizar");
		
		when(mockClienteRepository.findById(entity.getId())).thenReturn(Optional.of(entity));

		mockMvc.perform(put(getUrl().concat("cliente/"))
				.header(HttpHeaders.AUTHORIZATION, BEARER + obtainAccessToken(usernameApi, passwordApi))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content("{\"id\": -1, \"e_mail\": \"1atualizar@gmail.com\", \"name\": \"atualizar\" }"))
				.andExpect(status().isCreated());
		
		
		verify(mockClienteRepository).save(entity);

	}
 
	
	@Test
	public void listarCliente_200() throws Exception {

		List<ClienteEntity> clientes = new ArrayList<ClienteEntity>();

		for (Long i = 0L; i < 12; i++) {
			ClienteEntity entity = new ClienteEntity();
			entity.setId(i);
			entity.setEmail(i + "@teste.com.br");
			clientes.add(entity);
		}

		PageImpl<ClienteEntity> page = new PageImpl<ClienteEntity>(clientes);

		when(mockClienteRepository.findAll(any(PageRequest.class))).thenReturn(page);

		mockMvc.perform(
			get(getUrl().concat("cliente/?page=1&size=100"))
			.header(HttpHeaders.AUTHORIZATION, BEARER + obtainAccessToken(usernameApi, passwordApi)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.meta.page_number", is(1)));
		;

	}

	@Test
	public void adicionarProdutoFavorito_200() throws Exception {

		ProdutoDto dto = new ProdutoDto();
		dto.setId("e2f8ff7a-5a81-43a3-2089-e5aac4e3e08a");
		dto.setTitulo(UUID.randomUUID().toString());
		
		ProdutoDto dto2 = new ProdutoDto();
		dto2.setId("b601a106-8ee9-8e4d-8461-d8404d60bcb9");
		dto2.setTitulo(UUID.randomUUID().toString());

		ProdutoFavoritoId idP1 = new ProdutoFavoritoId();
		idP1.setIdCliente(1L);
		idP1.setIdProduto("b601a106-8ee9-8e4d-8461-d8404d60bcb9");

		ProdutoFavoritoId idP2 = new ProdutoFavoritoId();
		idP2.setIdCliente(1L);
		idP2.setIdProduto("e2f8ff7a-5a81-43a3-2089-e5aac4e3e08a");

		ProdutoFavoritoEntity entity = new ProdutoFavoritoEntity();
		entity.setId(idP1);
		
		ProdutoFavoritoEntity entity2 = new ProdutoFavoritoEntity();
		entity2.setId(idP2);

		Set<ProdutoFavoritoEntity> produtos = new HashSet<ProdutoFavoritoEntity>();
		produtos.add(entity2);
		produtos.add(entity);
		
		
		when(mockProdutoIntegration.getProdutoById("e2f8ff7a-5a81-43a3-2089-e5aac4e3e08a")).thenReturn(dto);
		when(mockProdutoIntegration.getProdutoById("b601a106-8ee9-8e4d-8461-d8404d60bcb9")).thenReturn(dto2);
		
		when(mockProdutoFavoritoRepository.saveAll(produtos)).thenReturn(produtos);

		mockMvc.perform(post(getUrl().concat("produto/cliente/1"))
				.header(HttpHeaders.AUTHORIZATION, BEARER + obtainAccessToken(usernameApi, passwordApi))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE).content(
						"{\"ids\" : [ \"e2f8ff7a-5a81-43a3-2089-e5aac4e3e08a\",\"b601a106-8ee9-8e4d-8461-d8404d60bcb9\"]}"))
				.andExpect(status().isCreated());

		verify(mockProdutoFavoritoRepository).saveAll(produtos);
	}

	@Test
	public void adicioanrProdutoFavorito_400() throws Exception {
		
		ProdutoDto dto = new ProdutoDto();
		dto.setId("e2f8ff7a-5a81-43a3-2089-e5aac4e3e08a");
		dto.setTitulo(UUID.randomUUID().toString());
		
		ProdutoDto dto2 = new ProdutoDto();
		dto2.setId("b601a106-8ee9-8e4d-8461-d8404d60bcb9");
		dto2.setTitulo(UUID.randomUUID().toString());

		ProdutoFavoritoId idP1 = new ProdutoFavoritoId();
		idP1.setIdCliente(-1L);
		idP1.setIdProduto("b601a106-8ee9-8e4d-8461-d8404d60bcb9");

		ProdutoFavoritoId idP2 = new ProdutoFavoritoId();

		idP2.setIdCliente(-1L);
		idP2.setIdProduto("e2f8ff7a-5a81-43a3-2089-e5aac4e3e08a");

		ProdutoFavoritoEntity entity = new ProdutoFavoritoEntity();
		entity.setId(idP1);
		
		ProdutoFavoritoEntity entity2 = new ProdutoFavoritoEntity();
		entity2.setId(idP2);

		Set<ProdutoFavoritoEntity> produtos = new HashSet<ProdutoFavoritoEntity>();
		produtos.add(entity2);
		produtos.add(entity);

		when(mockProdutoIntegration.getProdutoById("e2f8ff7a-5a81-43a3-2089-e5aac4e3e08a")).thenReturn(dto);
		when(mockProdutoIntegration.getProdutoById("b601a106-8ee9-8e4d-8461-d8404d60bcb9")).thenReturn(dto2);

		MensagemDto mensagemDto = new MensagemDto();
		mensagemDto.setCodigo("");
		mensagemDto.setMensagem("");
		
		when(mockProdutoFavoritoRepository.saveAll(produtos)).thenThrow(new BadRequestException(mensagemDto));

		mockMvc.perform(post(getUrl().concat("produto/cliente/-1"))
				.header(HttpHeaders.AUTHORIZATION, BEARER + obtainAccessToken(usernameApi, passwordApi))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE).content(
						"{\"ids\" : [ \"e2f8ff7a-5a81-43a3-2089-e5aac4e3e08a\",\"b601a106-8ee9-8e4d-8461-d8404d60bcb9\"]}"))
				.andExpect(status().isBadRequest());

		verify(mockProdutoFavoritoRepository).saveAll(produtos);
	}

	@Test
	public void removerProdutoFavorito_400() throws Exception {

		Long id = 1L;
		
		Set<String> produtos = new HashSet<String>()  ;
		produtos.add("e2f8ff7a-5a81-43a3-2089-e5aac4e3e08a" );
		produtos.add("b601a106-8ee9-8e4d-8461-d8404d60bcb9" );

		
		when(mockClienteRepository.isIdClienteExiste(id)).thenReturn(Optional.empty());

		mockMvc.perform(delete(getUrl().concat("produto/cliente/1"))
				.header(HttpHeaders.AUTHORIZATION, BEARER + obtainAccessToken(usernameApi, passwordApi))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE).content(
						"{\"ids\" : [ \"e2f8ff7a-5a81-43a3-2089-e5aac4e3e08a\",\"b601a106-8ee9-8e4d-8461-d8404d60bcb9\"]}"))
				.andExpect(status().isNotFound());
		
	}

	@Test
	public void removerProdutoFavorito_200() throws Exception {

		Long id = 1L;
		
		Set<String> produtos = new HashSet<String>()  ;
		produtos.add("e2f8ff7a-5a81-43a3-2089-e5aac4e3e08a" );
		produtos.add("b601a106-8ee9-8e4d-8461-d8404d60bcb9" );

		
		when(mockClienteRepository.isIdClienteExiste(id)).thenReturn(Optional.of(id));

		mockMvc.perform(delete(getUrl().concat("produto/cliente/1"))
				.header(HttpHeaders.AUTHORIZATION, BEARER + obtainAccessToken(usernameApi, passwordApi))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE).content(
						"{\"ids\" : [ \"e2f8ff7a-5a81-43a3-2089-e5aac4e3e08a\",\"b601a106-8ee9-8e4d-8461-d8404d60bcb9\"]}"))
				.andExpect(status().isNoContent());
		
		verify(mockProdutoFavoritoRepository).deleteByIdClienteAndIdProduto(id, produtos);
	}

	@Test
	public void listarProduto_200() throws Exception {

		List<ProdutoDto> produtos = new ArrayList<ProdutoDto>();

		for (Long i = 0L; i < 12; i++) {
			ProdutoDto dto = new ProdutoDto();
			dto.setId(UUID.randomUUID().toString());
			dto.setTitulo(UUID.randomUUID().toString());
			produtos.add(dto);
		}

		ProdutoPaginacaoDto page = new ProdutoPaginacaoDto();
		page.setProdutos(produtos);
		page.setMeta(new PaginacaoDto(1, 100, null, null, false));

		when(mockProdutoIntegration.getAll(1)).thenReturn(page);

		mockMvc.perform(get(getUrl().concat("produto/")).header(HttpHeaders.AUTHORIZATION,
				BEARER + obtainAccessToken(usernameApi, passwordApi))).andExpect(status().isOk())
				.andExpect(jsonPath("$.meta.page_number", is(1)));
		;

	}

	@Test
	public void produtoById_200() throws Exception {

		ProdutoDto dto = new ProdutoDto();
		dto.setId("e2f8ff7a-5a81-43a3-2089-e5aac4e3e08a");
		dto.setTitulo(UUID.randomUUID().toString());

		when(mockProdutoIntegration.getProdutoById("e2f8ff7a-5a81-43a3-2089-e5aac4e3e08a")).thenReturn(dto);

		mockMvc.perform(get(getUrl().concat("produto/e2f8ff7a-5a81-43a3-2089-e5aac4e3e08a"))
				.header(HttpHeaders.AUTHORIZATION, BEARER + obtainAccessToken(usernameApi, passwordApi)))
				.andExpect(status().isOk());
		;

	}

	
	private String obtainAccessToken(String username, String pass) throws Exception {

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("username", username);
		params.add("password", pass);

		ResultActions result = mockMvc
				.perform(post("/oauth/token").params(params).with(httpBasic(user, this.password))
						.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

		if (result.andReturn().getResponse().getStatus() != HttpStatus.OK.value()) {
			return "";
		}

		String resultString = result.andReturn().getResponse().getContentAsString();

		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}

}