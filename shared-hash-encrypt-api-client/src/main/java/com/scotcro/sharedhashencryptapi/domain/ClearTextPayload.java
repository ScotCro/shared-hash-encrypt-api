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

public class ClearTextPayload implements Serializable {
    private static final long serialVersionUID = 810150005069455996L;

    public static final String MEDIA_TYPE = "application/json";

    private String clearText;

    public ClearTextPayload() {
        super();
    }

    public ClearTextPayload(final String clearText) {
        this();
        this.clearText = clearText;
    }

    public String getClearText () {
        return clearText;
    }

    public void setClearText (final String clearText) {
        this.clearText = clearText;
    }
}
