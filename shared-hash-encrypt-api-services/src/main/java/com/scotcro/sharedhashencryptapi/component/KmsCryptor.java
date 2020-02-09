/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.api.client.util.Base64;
import com.google.cloud.kms.v1.KeyManagementServiceClient;
import com.google.protobuf.ByteString;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
@Component
public class KmsCryptor implements InitializingBean {

    @Value("${kms-crptor.project-id}")
    private String projectId;

    @Value("${kms-crptor.location}")
    private String location;

    @Value("${kms-crptor.key-ring-id}")
    private String keyRingId;

    @Value("${kms-crptor.key-id}")
    private String keyId;

    @Resource
    private Parallelizer<String> encryptionDecryptionParallelizer;

    @Resource
    private KmsHelper kmsHelper;

    private KeyManagementServiceClient client;
    private String resourceName;

    @Override
    public void afterPropertiesSet() {
        try {
            client = kmsHelper.create();

        } catch (final IOException exception) {
            throw new RuntimeException("unable to create KeyManagementServiceClient", exception);
        }

        resourceName = kmsHelper.formatKryptoKeyName(projectId, location, keyRingId, keyId);
    }

    public String encrypt(final String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }

        try {
            return Base64.encodeBase64String(client.encrypt(resourceName,
                    ByteString.copyFrom(source.getBytes("UTF-8")))
                    .getCiphertext().toByteArray());

        } catch (final UnsupportedEncodingException exception) {
            throw new RuntimeException("unable to encrypt data", exception);
        }
    }

    public List<String> encryptList(final List<String> sourceList) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }

        final List<Callable<String>> callables = new ArrayList<>();
        for(final String source : sourceList) {
            callables.add(new Callable<String>() {
                @Override
                public String call() {
                    return encrypt(source);
                }
            });
        }

        final List<String> results = encryptionDecryptionParallelizer.parallelize(callables);
        return results;
    }

    public String decrypt(final String cipheredAndBase64ed) {
        try {
            return new String(client.decrypt(resourceName,
                    ByteString.copyFrom(Base64.decodeBase64(cipheredAndBase64ed)))
                        .getPlaintext().toByteArray(), "UTF-8");

        } catch (final UnsupportedEncodingException exception) {
            throw new RuntimeException("unable to encrypt data", exception);
        }
    }

    public List<String> decryptList(final List<String> sourceList) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }

        final List<Callable<String>> callables = new ArrayList<>();
        for(final String source : sourceList) {
            callables.add(new Callable<String>() {
                @Override
                public String call() {
                    return decrypt(source);
                }
            });
        }

        final List<String> results = encryptionDecryptionParallelizer.parallelize(callables);
        return results;
    }

    @PreDestroy
    public void destroy() {
        if (client != null) {
            client.close();
            client = null;
        }
    }
}
