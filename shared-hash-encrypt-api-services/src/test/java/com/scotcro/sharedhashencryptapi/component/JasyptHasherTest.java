/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.component;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.jasypt.salt.SaltGenerator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.scotcro.sharedhashencryptapi.component.Hasher;
import com.scotcro.sharedhashencryptapi.component.JasyptHasher;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
public class JasyptHasherTest {

	private SaltGenerator mockSaltGenerator;
	
	private Hasher subject;

    @Before
    public void setupForEachTest() {
    	mockSaltGenerator = Mockito.mock(SaltGenerator.class);
    	subject = new JasyptHasher(1, mockSaltGenerator);
    }
    
    @Test
    public void hashAndCompare_BehavesAsExpected() {
    	final byte[] saltBytes = "whatever".getBytes();
    	when(mockSaltGenerator.generateSalt(anyInt())).thenReturn(saltBytes);
        final String clearText = "clear-text";
        final String wrongClearText = "this is wrong";

        final String hashedResult = subject.hash(clearText);
        assertTrue(subject.isValid(clearText, hashedResult));
        assertFalse(subject.isValid(wrongClearText, hashedResult));
        
        verify(mockSaltGenerator, times(3)).generateSalt(eq(8));
        verify(mockSaltGenerator, times(5)).includePlainSaltInEncryptionResults();
        
        Mockito.verifyNoMoreInteractions(mockSaltGenerator);
    }
}
