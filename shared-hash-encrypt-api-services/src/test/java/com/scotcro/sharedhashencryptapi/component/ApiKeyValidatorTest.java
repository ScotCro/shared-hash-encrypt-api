/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.component;


import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.scotcro.sharedhashencryptapi.component.ApiKeyValidator;
import com.scotcro.sharedhashencryptapi.entity.ClientApiEntity;
import com.scotcro.sharedhashencryptapi.repository.ClientApiEntityRepository;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
@ExtendWith(MockitoExtension.class)
public class ApiKeyValidatorTest {
    private static final String CANDIDATE_KEY = "candidate-key";

    @Mock
    private ClientApiEntityRepository mockRepo;

    @InjectMocks
    private ApiKeyValidator subject;

    @Test
    public void vaidateApiKeyAndGetIssuedTo_NotFound_NullReturned() {
        final String result = subject.validateApiKeyAndGetIssuedTo(CANDIDATE_KEY);
        assertThat(result, nullValue());
        verify(mockRepo).findActiveClientApiKey(same(CANDIDATE_KEY));
    }

    @Test
    public void vaidateApiKeyAndGetIssuedTo_Found_EntityIssuedToReturned() {
        final String issuedTo = "issued_to";
        final ClientApiEntity entity = new ClientApiEntity();
        entity.setIssuedTo(issuedTo);
        when(mockRepo.findActiveClientApiKey(same(CANDIDATE_KEY))).thenReturn(entity);
        final String result = subject.validateApiKeyAndGetIssuedTo(CANDIDATE_KEY);
        assertThat(result, sameInstance(issuedTo));
        verify(mockRepo).findActiveClientApiKey(same(CANDIDATE_KEY));
    }
}
