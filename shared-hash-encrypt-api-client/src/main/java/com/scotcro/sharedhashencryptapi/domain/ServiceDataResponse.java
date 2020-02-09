/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.domain;

import java.util.List;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
public class ServiceDataResponse<T> extends ServiceResponse {
	private static final long serialVersionUID = 4030378312030361649L;

	private T data;
	
	public ServiceDataResponse() {
		super("200", "OK");
		this.data = null;
	}
	
	public ServiceDataResponse(T data) {
		super("200", "OK");
		this.data = data;
	}

	public ServiceDataResponse(T data, List<String> codes, List<String> messages) {
		super(codes, messages);
		this.data = data;
	}
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
