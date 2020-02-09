/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
public class EncryptedBase64EncodedPayload implements Serializable {
    private static final long serialVersionUID = -8189380004121239698L;

    public static final String MEDIA_TYPE = "application/json";

    private String encryptedAndBase64EncodedText;

    public EncryptedBase64EncodedPayload() {
        super();
    }

    public EncryptedBase64EncodedPayload(final String encryptedAndBase64EncodedResult) {
        this();
        this.encryptedAndBase64EncodedText = encryptedAndBase64EncodedResult;
    }

    public String getEncryptedAndBase64EncodedText () {
        return encryptedAndBase64EncodedText;
    }

    public void setEncryptedAndBase64EncodedText (final String encryptedAndBase64EncodedText) {
        this.encryptedAndBase64EncodedText = encryptedAndBase64EncodedText;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
