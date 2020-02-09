/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.controller;

import java.util.Collections;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scotcro.sharedhashencryptapi.service.DbConnectionService;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
@RestController
public class MonitorController {

    @Resource
    private DbConnectionService dbConnectionService;

    @RequestMapping("/alive")
    public AliveResponse aliveCheck() {
        return new AliveResponse();
    }

    @RequestMapping("/ready")
    public ReadyResponse readyCheck() {
        return new ReadyResponse(Collections.emptyMap(), dbConnectionService.isGoodConnectionAvailable());
    }
}
