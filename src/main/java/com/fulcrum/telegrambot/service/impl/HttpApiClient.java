package com.fulcrum.telegrambot.service.impl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;

import java.util.Optional;
import java.util.function.Function;

public class HttpApiClient {

    public final RestTemplate restTemplate;

    public HttpApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T, R> Optional<T> executeGetRequest(UriComponents urlComponents, ParameterizedTypeReference<R> typeRef, Function<R, T> responseMapper) {
        try {
            return Optional.of(restTemplate.exchange(toUrlString(urlComponents), HttpMethod.GET, new HttpEntity<>(buildDefaultHeaders()), typeRef))
                    .filter(resp -> resp.getStatusCode() == HttpStatus.OK)//
                    .map(HttpEntity::getBody)//
                    .map(responseMapper);
        } catch (RestClientException ex) {
            throw new RestClientException("During data access from external API", ex);
        }
    }

    protected String toUrlString(UriComponents urlComponents) {
        return urlComponents.encode().toUriString();
    }

    private HttpHeaders buildDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

}
