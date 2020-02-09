/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */
package com.scotcro.sharedhashencryptapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scotcro.sharedhashencryptapi.entity.ClientApiEntity;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
public interface ClientApiEntityRepository extends JpaRepository<ClientApiEntity, String> {
    @Query(nativeQuery=true,
           value="select * from client_api_keys where api_key = :apiKey and effective_start_date <= CURRENT_TIMESTAMP and (effective_end_date is NULL or effective_end_date > CURRENT_TIMESTAMP) order by effective_start_date asc limit 1")
    ClientApiEntity findActiveClientApiKey(@Param("apiKey") final String apiKey);
}
