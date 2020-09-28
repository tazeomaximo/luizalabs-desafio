package br.com.luizalabs.desafio.integration;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.luizalabs.desafio.dto.ProdutoDto;

@Component
public class ProdutoIntegration {
	
	private static final String UTF_8 = "UTF-8";

	@Value("${rest.template.connect.timeout}")
	private Integer connectTimeout;
	
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

	public ProdutoDto getProdutoById(String idProduto) {

		ResponseEntity<ProdutoDto> response = getRest().getForEntity(
				"http://challenge-api.luizalabs.com/api/product/".concat(idProduto).concat("/"), ProdutoDto.class);

		if(response.getStatusCode().series() == Series.SUCCESSFUL)		
			return response.getBody();
		return null;
	}

}
