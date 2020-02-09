/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
public class EncryptedBase64EncodedListPayload implements Serializable {
    private static final long serialVersionUID = -6959275990405276737L;

    public static final String MEDIA_TYPE = "application/json";

    private List<String> encryptedAndBase64EncodedTextValues;

    public EncryptedBase64EncodedListPayload() {
        super();
        this.encryptedAndBase64EncodedTextValues = new ArrayList<>();
    }

    public EncryptedBase64EncodedListPayload(final List<String> encryptedAndBase64EncodedTextValues) {
        this();
        this.encryptedAndBase64EncodedTextValues = encryptedAndBase64EncodedTextValues;
    }

    public List<String> getEncryptedAndBase64EncodedTextValues () {
        return encryptedAndBase64EncodedTextValues;
    }

    public void setEncryptedAndBase64EncodedTextValues (final List<String> encryptedAndBase64EncodedTextValues) {
        this.encryptedAndBase64EncodedTextValues = encryptedAndBase64EncodedTextValues;
    }
}
