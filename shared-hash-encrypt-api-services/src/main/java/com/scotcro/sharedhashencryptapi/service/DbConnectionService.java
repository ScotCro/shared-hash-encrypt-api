/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */
package com.scotcro.sharedhashencryptapi.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.scotcro.sharedhashencryptapi.repository.ClientApiEntityRepository;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
@Service
public class DbConnectionService {

    @Resource
    private ClientApiEntityRepository clientApiEntityRepository;

    @Transactional(propagation=Propagation.SUPPORTS, timeout=1)
    public boolean isGoodConnectionAvailable() {
        try {
            clientApiEntityRepository.count();
            return true;

        } catch (final Exception e) {
            return false;
        }
    }
}
