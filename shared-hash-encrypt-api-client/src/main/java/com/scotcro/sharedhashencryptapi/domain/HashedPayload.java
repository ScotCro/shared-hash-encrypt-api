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
public class HashedPayload implements Serializable {
    private static final long serialVersionUID = 7415858843164650113L;

    public static final String MEDIA_TYPE = "application/json";

    private String hashedText;

    public HashedPayload() {
        super();
    }

    public HashedPayload(final String hashedText) {
        this();
        this.hashedText = hashedText;
    }

    public String getHashedText () {
        return hashedText;
    }

    public void setHashedText (final String hashedText) {
        this.hashedText = hashedText;
    }
}
