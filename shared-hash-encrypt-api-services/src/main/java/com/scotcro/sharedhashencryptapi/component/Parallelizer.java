/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.collections4.CollectionUtils;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
public class Parallelizer<T> {
    private final ExecutorService executor;

    public Parallelizer(final Integer poolSize) {
        this.executor = Executors.newFixedThreadPool(poolSize);
    }

    public List<T> parallelize(final List<Callable<T>> callables) {
        if (CollectionUtils.isEmpty(callables)) {
            return Collections.emptyList();
        }

        final List<Future<T>> futures = new ArrayList<>();
        for(final Callable<T> callable : callables) {
            futures.add(executor.submit(callable));
        }

        final List<T> results = new ArrayList<>(futures.size());
        for(Integer index = 0, limit = futures.size(); index < limit; index++) {
            try {
                results.add(index, futures.get(index).get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("unable to complete parallelized operation over list", e);
            }
        }
        return results;
    }
}
