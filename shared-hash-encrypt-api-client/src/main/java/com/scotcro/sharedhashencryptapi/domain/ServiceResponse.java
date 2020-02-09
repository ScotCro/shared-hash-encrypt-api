/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
public class ServiceResponse implements Serializable {
	private static final long serialVersionUID = 8956851540209249476L;

	private List<String> codes;
	private List<String> messages;
	
	public ServiceResponse() {
		super();
		this.codes = new ArrayList<>();
		this.messages = new ArrayList<>();
	}
	
	public ServiceResponse(final String code, final String message) {
		this();
		this.getCodes().add(code);
		this.getMessages().add(message);
	}
	
	public ServiceResponse(final List<String> codes, final List<String> messages) {
		this();
		this.getCodes().addAll(codes);
		this.getMessages().addAll(messages);
	}

	public boolean isSuccessful() {
		return codes != null && !codes.isEmpty()
				&& "200".contentEquals(codes.get(0));
	}

	public List<String> getCodes() {
		return codes;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}
