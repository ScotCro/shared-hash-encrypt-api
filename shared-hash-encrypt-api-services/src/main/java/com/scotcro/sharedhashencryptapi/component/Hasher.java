/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.component;

/**
 * Implementers of this interface will provide methods to create a hashed value and verify it against the original clear text value.
 * 
 * @author Steve Willis, Employee ofs ScotCro LLC
 *
 */
public interface Hasher {
	String hash(final String source);
	boolean isValid(final String source, final String andHash);
}
