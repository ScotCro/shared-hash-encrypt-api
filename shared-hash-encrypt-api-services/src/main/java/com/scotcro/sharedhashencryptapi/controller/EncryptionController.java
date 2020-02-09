/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scotcro.sharedhashencryptapi.component.Hasher;
import com.scotcro.sharedhashencryptapi.component.KmsCryptor;
import com.scotcro.sharedhashencryptapi.domain.ClearTextListPayload;
import com.scotcro.sharedhashencryptapi.domain.ClearTextPayload;
import com.scotcro.sharedhashencryptapi.domain.EncryptedBase64EncodedListPayload;
import com.scotcro.sharedhashencryptapi.domain.EncryptedBase64EncodedPayload;
import com.scotcro.sharedhashencryptapi.domain.HashValidationPayload;
import com.scotcro.sharedhashencryptapi.domain.HashedPayload;
import com.scotcro.sharedhashencryptapi.domain.ServiceDataResponse;
import com.scotcro.sharedhashencryptapi.domain.ServiceListResponse;
import com.scotcro.sharedhashencryptapi.domain.ServiceResponse;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
@RestController
@CrossOrigin
public class EncryptionController {

    @Resource
    private KmsCryptor kmsCryptor;

    @Resource
    private Hasher hasher;

    @RequestMapping(value="/list-encryptions", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ServiceListResponse<String> encryptList(@RequestBody final ClearTextListPayload request) {
        final List<String> results = kmsCryptor.encryptList(request.getClearTextValues());
        return new ServiceListResponse<>(results);
    }

    @RequestMapping(value="/encryptions", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ServiceDataResponse<EncryptedBase64EncodedPayload> encrypt(@RequestBody final ClearTextPayload request) {

        final EncryptedBase64EncodedPayload payload = new EncryptedBase64EncodedPayload(kmsCryptor.encrypt(request.getClearText()) );
        final ServiceDataResponse<EncryptedBase64EncodedPayload> result = new ServiceDataResponse<EncryptedBase64EncodedPayload>(payload) {
            private static final long serialVersionUID = -1192266319187318844L;

            @Override
            public String toString() {
                return ToStringBuilder.reflectionToString(this);
            }
        };
        return result;
    }

    @RequestMapping(value="/decryptions", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ServiceDataResponse<ClearTextPayload> decrypt(@RequestBody final EncryptedBase64EncodedPayload request) {
        return new ServiceDataResponse<>(new ClearTextPayload(kmsCryptor.decrypt(request.getEncryptedAndBase64EncodedText())));
    }

    @RequestMapping(value="/list-decryptions", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ServiceListResponse<String> decryptList(@RequestBody final EncryptedBase64EncodedListPayload request) {
        return new ServiceListResponse<>(kmsCryptor.decryptList(request.getEncryptedAndBase64EncodedTextValues()));
    }

    @RequestMapping(value="/hashes", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ServiceDataResponse<HashedPayload> hash(@RequestBody final ClearTextPayload request) {
        return new ServiceDataResponse<>(new HashedPayload(hasher.hash(request.getClearText())));
    }

    @RequestMapping(value="/hash-cleartext-validations", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ServiceResponse validate(@RequestBody final HashValidationPayload request) {
        return hasher.isValid(request.getClearText(), request.getHash()) ?
                new ServiceResponse("200", "VALID") :
                new ServiceResponse("0", "INVALID");
    }
}
