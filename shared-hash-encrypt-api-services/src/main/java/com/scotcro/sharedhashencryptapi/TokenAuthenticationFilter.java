/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.scotcro.sharedhashencryptapi.client.HeaderNames;
import com.scotcro.sharedhashencryptapi.component.ApiKeyValidator;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
public class TokenAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {
    
    @Resource
    private ApiKeyValidator apiKeyValidator;

    @Override
    protected Object getPreAuthenticatedCredentials(
            final HttpServletRequest httpServletRequest
        ) {
        return "";
    }

    @Override
    protected Object getPreAuthenticatedPrincipal (final HttpServletRequest request) {
        final String apiKey = request.getHeader(HeaderNames.CLIENT_API_KEY);
        if (StringUtils.isBlank(apiKey)) {
            return null;
        }
        final String issuedTo = apiKeyValidator.validateApiKeyAndGetIssuedTo(apiKey);
        if (StringUtils.isNotBlank(issuedTo)) {
            return new PrincipalUser(issuedTo);
        }

        return null;
    }

}
