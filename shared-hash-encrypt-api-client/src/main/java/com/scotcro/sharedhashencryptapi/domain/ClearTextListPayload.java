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
public class ClearTextListPayload implements Serializable {
    private static final long serialVersionUID = -8704288843450787731L;

    public static final String MEDIA_TYPE = "application/json";

    private List<String> clearTextValues;

    public ClearTextListPayload() {
        super();
        this.clearTextValues = new ArrayList<>();
    }

    public ClearTextListPayload(final List<String> clearTextValues) {
        this();
        this.clearTextValues.addAll(clearTextValues);
    }

    public List<String> getClearTextValues() {
        return clearTextValues;
    }

    public void setClearTextValues(final List<String> clearTextValues) {
        this.clearTextValues = clearTextValues;
    }
}
