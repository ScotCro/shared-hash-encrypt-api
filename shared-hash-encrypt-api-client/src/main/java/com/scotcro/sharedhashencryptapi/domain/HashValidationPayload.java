/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.domain;

import java.io.Serializable;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
public class HashValidationPayload implements Serializable {
    private static final long serialVersionUID = -573716299703352437L;
    
    public static final String MEDIA_TYPE = "application/json";

    private String clearText;
    private String hash;

    public String getClearText () {
        return clearText;
    }

    public void setClearText (final String clearText) {
        this.clearText = clearText;
    }

    public String getHash () {
        return hash;
    }

    public void setHash (final String hash) {
        this.hash = hash;
    }
}
