/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.scotcro.sharedhashencryptapi.domain.ClearTextListPayload;
import com.scotcro.sharedhashencryptapi.domain.EncryptedBase64EncodedListPayload;
import com.scotcro.sharedhashencryptapi.domain.ServiceListResponse;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
public class CryptorClient {
	//Use this class be using @Bean and constructor injectionO

	private static final Logger LOG = LoggerFactory.getLogger(CryptorClient.class);

    static final String NOT_CONFIGURED_PROPERLY_MSG = "cryptor.client.base-url or cryptor.client.encrypt-list-path isn't configured in properties correctly";
    static final String NON_OK_MSG_FMT = "unable to encrypt or decrypt string(s); HTTP_STATUS: %d, REASON_PHRASE: %s";
    static final String RCE_MSG = "unable to encrypt or decrypt string(s)";

    @Resource
    private RestTemplate restTemplate;

    private final String baseUrl;

    private final String encryptListPath;

    private final String decryptListPath;

    private final String clientApiKey;

    public CryptorClient(
    		final String baseUrl, 
    		final String encryptListPath, 
    		final String decryptListPath, 
    		final String clientApiKey
    	) {
    	super();
    	this.baseUrl = baseUrl;
    	this.encryptListPath = encryptListPath;
    	this.decryptListPath = decryptListPath;
    	this.clientApiKey = clientApiKey;
    }
    
    public List<String> encryptList(final List<String> clearTextList) {
        final URI uri;

        try {
            uri = new URIBuilder(baseUrl + encryptListPath).build();
        } catch (final URISyntaxException | NullPointerException e) {
                throw new IllegalStateException(NOT_CONFIGURED_PROPERLY_MSG, e);
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");
        headers.set(HeaderNames.CLIENT_API_KEY, clientApiKey);

        try {
            final HttpEntity<ClearTextListPayload> httpEntity = new HttpEntity<>(
                    new ClearTextListPayload(clearTextList), headers);
            final ResponseEntity<ServiceListResponse<String>> response = restTemplate.exchange(uri, HttpMethod.POST,
                    httpEntity, new ParameterizedTypeReference<ServiceListResponse<String>>() {});

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody().getData();
            }

            final String message = String.format(NON_OK_MSG_FMT, response.getStatusCode().value(),
                    response.getStatusCode().getReasonPhrase());
            throw new RestCallFailedException(message);

        } catch (final RestClientException e) {
            LOG.error(RCE_MSG, e);
            throw new RestCallFailedException(RCE_MSG, e);
        }
    }

    public List<String> decryptList(final List<String> encryptedBase64EncodedList) {
        final URI uri;

        try {
            uri = new URIBuilder(baseUrl + decryptListPath).build();
        } catch (final URISyntaxException | NullPointerException e) {
                throw new IllegalStateException(NOT_CONFIGURED_PROPERLY_MSG, e);
        }

        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Content-Type", "application/json");
        headers.set(HeaderNames.CLIENT_API_KEY, clientApiKey);

        try {
            final HttpEntity<EncryptedBase64EncodedListPayload> httpEntity = new HttpEntity<>(
                    new EncryptedBase64EncodedListPayload(encryptedBase64EncodedList), headers);
            final ResponseEntity<ServiceListResponse<String>> response = restTemplate.exchange(uri, HttpMethod.POST,
                    httpEntity, new ParameterizedTypeReference<ServiceListResponse<String>>() {});

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody().getData();
            }

            final String message = String.format(NON_OK_MSG_FMT, response.getStatusCode().value(),
                    response.getStatusCode().getReasonPhrase());
            throw new RestCallFailedException(message);

        } catch (final RestClientException e) {
            LOG.error(RCE_MSG, e);
            throw new RestCallFailedException(RCE_MSG, e);
        }
    }
}
