/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.domain;

import java.util.Collections;
import java.util.List;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
public class ServiceListResponse<T> extends ServiceDataResponse<List<T>> {
	private static final long serialVersionUID = 7016483802643911376L;

	public ServiceListResponse(List<T> data) {
		super(data);
	}
	
	public ServiceListResponse(List<T> data, List<String> codes, List<String> messages) {
		super(data, codes, messages);
	}
	
	public ServiceListResponse(List<T> data, String code, String messages) {
		super(data, Collections.singletonList(code), Collections.singletonList(messages));
	}
}
