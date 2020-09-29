package br.com.luizalabs.desafio.integration;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.luizalabs.desafio.dto.ProdutoDto;
import br.com.luizalabs.desafio.dto.ProdutoPaginacaoDto;

@Component
public class ProdutoIntegration {
	
	private static final String UTF_8 = "UTF-8";

	@Value("${rest.template.connect.timeout}")
	private Integer connectTimeout;
	
	@Value("${integration.url.luizalabs.challenge}")
	private String urlchallnge;
	
	private RestTemplate rest;
	
	public RestTemplate getRest() {
		if (rest == null) {
			
			final SimpleClientHttpRequestFactory httpFactory = new SimpleClientHttpRequestFactory();
			httpFactory.setConnectTimeout(this.connectTimeout);
			
			rest = new RestTemplate(httpFactory);
			
			rest.setRequestFactory(new BufferingClientHttpRequestFactory(httpFactory));
			rest.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(UTF_8)));
			
			rest.setErrorHandler(new WsResponseErrorHandler());
		}

		return rest;
	}

	public void setRest(RestTemplate rest) {
		this.rest = rest;
	}

	@Cacheable(cacheNames = "ProdutoDto", key="#idProduto")
	public ProdutoDto getProdutoById(String idProduto) {

		ResponseEntity<ProdutoDto> response = getRest().getForEntity(
				urlchallnge.concat(idProduto).concat("/"), ProdutoDto.class);

		return (ProdutoDto) getObjectReturn(response);
	}

	private Object getObjectReturn(ResponseEntity<?> response) {
		if(response.getStatusCode().series() == Series.SUCCESSFUL)		
			return response.getBody();
		return null;
	}

	public ProdutoPaginacaoDto getAll(Integer page) {
		
		ResponseEntity<ProdutoPaginacaoDto> response = getRest().getForEntity(
				urlchallnge.concat("?page=").concat(page.toString()), ProdutoPaginacaoDto.class);
		
		return (ProdutoPaginacaoDto) getObjectReturn(response);
	}

}
