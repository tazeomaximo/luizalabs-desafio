package br.com.luizalabs.desafio.integration;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class WsResponseErrorHandler implements ResponseErrorHandler{
	
	private static final Logger LOG = LoggerFactory.getLogger(WsResponseErrorHandler.class);

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		LOG.info("[info] - response status code: {}", response.getRawStatusCode());

		return (response.getStatusCode().series() == Series.CLIENT_ERROR
		    || response.getStatusCode().series() == Series.SERVER_ERROR);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
//		final RequestException requestException = this.getMapError().get(response.getRawStatusCode());

		LOG.info("[info] - Convertendo response exception");

//		final String jsonResponse = StreamUtils.copyToString(response.getBody(), Charset.forName("UTF-8"));
//
//		final MessageError[] errors =
//		    (MessageError[]) ConverterUtils.deserializable(jsonResponse, MessageError[].class);
//
//		if (requestException != null) {
//			requestException.setMessageError(errors[0]);
//			throw requestException;
//		}
		
	}

}
