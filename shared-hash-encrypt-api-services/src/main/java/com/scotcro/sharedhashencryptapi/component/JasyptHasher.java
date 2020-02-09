/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.component;

import org.jasypt.digest.PooledStringDigester;
import org.jasypt.salt.SaltGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A completely local concrete implementation of the Hasher based on the Jasypt crypto library.
 * 
 * @author Steve Willis, Employee of ScotCro LLC
 *
 */
public class JasyptHasher implements Hasher {
	private static final Logger LOG = LoggerFactory.getLogger(JasyptHasher.class);
	private final PooledStringDigester digester;
	
	public JasyptHasher(final Integer poolSize, final SaltGenerator saltGenerator) {
		digester = new PooledStringDigester();
		digester.setPoolSize(poolSize);
		digester.setAlgorithm("SHA3-512");
		digester.setIterations(5000);
		digester.setSaltGenerator(saltGenerator);
	}
	
	/**
	 * Returns a one way hash of the passed source
	 * @param source The source string to be hashed
	 * @return The hashed result or null if source is blank or null
	 */
	@Override
	public String hash(String source) {
		return digester.digest(source);
	}

	/**
	 * Returns a boolean indication of the validity of the hash given the source string.
	 * @param source the clear text string
	 * @param andHash the hashed value with which the hashed source is compared
	 * @return a boolean true if the hashed source result matches the passed andHash; otherwise false
	 */
	@Override
	public boolean isValid(String source, String andHash) {
		LOG.info("source: " + source);
		LOG.info("andHash: " + andHash);
		return digester.matches(source, andHash);
	}
}
