/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.client;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
public class RestCallFailedException extends RuntimeException {
	private static final long serialVersionUID = 1067355469972183610L;

	public RestCallFailedException(final String message) {
		super(message);
	}
	
	public RestCallFailedException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
