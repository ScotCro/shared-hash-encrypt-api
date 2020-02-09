/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.component;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.google.cloud.kms.v1.CryptoKeyName;
import com.google.cloud.kms.v1.KeyManagementServiceClient;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
@Component
public class KmsHelper {

    public KeyManagementServiceClient create () throws IOException {
        return KeyManagementServiceClient.create();
    }

    public String formatKryptoKeyName (
            final String projectId,
            final String location,
            final String keyRingId,
            final String keyId
        ) {

        return CryptoKeyName.format(projectId, location, keyRingId, keyId);
    }
}
