/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.component;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.scotcro.sharedhashencryptapi.entity.ClientApiEntity;
import com.scotcro.sharedhashencryptapi.repository.ClientApiEntityRepository;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
@Component
public class ApiKeyValidator {

    @Resource
    private ClientApiEntityRepository clientApiEntityRepository;

    @Transactional(propagation=Propagation.SUPPORTS)
    public String validateApiKeyAndGetIssuedTo(final String candidate) {
        final ClientApiEntity entity = clientApiEntityRepository.findActiveClientApiKey(candidate);
        return entity == null ? null : entity.getIssuedTo();
    }
}
