/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.controller;

import java.io.Serializable;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
public class AliveResponse implements Serializable {
    private static final long serialVersionUID = -5325126701661498578L;

    private Boolean alive = true;

    public boolean isAlive() {
        return alive == null ? false : alive;
    }

    public Boolean getAlive() {
        return isAlive();
    }

    public void setAlive(final Boolean alive) {
        this.alive = alive;
    }
}
