/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.controller;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
public class ReadyResponse implements Serializable {
    private static final long serialVersionUID = -1036848264306940412L;

    public Map<String, Boolean> endpointsAliveMap = new HashMap<>();
    public Boolean databaseConnectionActive = false;

    public ReadyResponse (final Map<String, Boolean> endpointsAliveMap, final Boolean databaseConnectionActive) {
        this.endpointsAliveMap = endpointsAliveMap;
        this.databaseConnectionActive = databaseConnectionActive;
    }

    public Map<String, Boolean> getEndpointsAliveMap () {
        return endpointsAliveMap;
    }

    public void setEndpointsAliveMap (final Map<String, Boolean> endpointsAliveMap) {
        this.endpointsAliveMap = endpointsAliveMap;
    }

    public Boolean getDatabaseConnectionActive () {
        return databaseConnectionActive;
    }

    public void setDatabaseConnectionActive (final Boolean databaseConnectionActive) {
        this.databaseConnectionActive = databaseConnectionActive;
    }
}
