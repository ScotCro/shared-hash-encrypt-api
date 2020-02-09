/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */

package com.scotcro.sharedhashencryptapi;

import org.apache.commons.lang3.StringUtils;
import org.jasypt.salt.SaltGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.scotcro.sharedhashencryptapi.component.Hasher;
import com.scotcro.sharedhashencryptapi.component.JasyptHasher;
import com.scotcro.sharedhashencryptapi.component.Parallelizer;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
@SpringBootApplication
@PropertySource(ignoreResourceNotFound=false,value={
        "classpath:application.properties"})
@EnableJpaRepositories(basePackages= {"com.scotcro.sharedhashencryptapi.repository"})
@ComponentScan(basePackages={"com.scotcro"})
@EnableScheduling
public class SharedHashEncryptApiApp implements WebMvcConfigurer {

    @Bean
    public Parallelizer<String> encryptDecryptParallelizer(
    		@Value("${encryption.decryption.parallel.execution.pool.size:10}") final Integer poolSize
    	) {
        return new Parallelizer<>(poolSize);
    }

    @Bean
    public SaltGenerator saltGenerator() {
        //TODO: We're going to need a salt generator that gens a fixed salt and rotates those fixed salts.
    	return new SaltGenerator() {
    		private static final String SALT_CHARS = "XkoNuplqulayyhul4lHbcqNgfimWqPfArHI289Dd6xA0WFcTzGWtzH3GchLgUYiWF7xiAke3B9QpyGnnTDujK5Pxpnq9kDoSZkljF7APErRP4X0nUeq0jGkNdUvrZ3yAZQGFsLU7TxAv6KrB6E4NHioSbBP8x08ObOnoses3lF43ZWh3UFQiVR16fpvVsivJLqZ4JdOncTLvRfvv7Jm2tOsvZRR8SEHSOluSobqQAJTZYE19GTIgrYoI9kyQnLxvCse9COANPmlpka6rCk33TAqeyPThjHP9hQ80jFn1TSBuuOxnlUozAtOYYSAucp2lE23lK2aW3O00EIBS8YHJKLnQBKv8uGWLjAC3Md8OMYhkXLBnND8YnqPVtRVJt6Jp6I1mMM6rVyfc78pTK0Mjyy9vrqMWtuhhAkjHEgF5clVGfpy2dUhLYpnJZxlkpsG3ZmPt07xpOpEtWH9zLG2N59AbJ8d9ld19l8PtPR6XnNGry6xXUkrd3sY1RCgvCKt5Mbs54moK9CTjRxLmiWtgdEz3Zp8MS3x0af3bQG33mpSJQxCONcAUHVLld2ScHPvn1tmYzPIC0E3vMCGA1nBpmUkSn9IObepay1sfyXDWGxicCDSQ8oebVpKzE2jxZ1W51EyOKOxTO7EItB0GLavRjtushj6BqUK4uRaaLjGA3CiuAvIaehFAnGWfLSRc65Zav3Kx9ufsZQTHdKEO8S9CLze0Z0Gkp6axk48UUHUH1aWHdlRdZGZa1vy7WS1WH04KfWGIgSHJBvJSpSt4gsmq3NvznealgA7jePiiIj4bBEaCyhCcDFmYKI1mYiLR21UxLk21dziW0N8Cjcu4pa7eNh8mxWLD5KWqX0IqOu3xUmWjIlWpmy5q0TkaDbyQIsJcV0OEkPfGy88KCiXbDmDzvqYmmPj6Cz1CpaK7rE9dSprsDmII0NPMzt33qmE2iFoRDfiAVoGa9I5R9VB5pxxViU8vngubRs0Epofd7456TucHIPMUERJp2AAeeicaWSBn";
    		
			@Override
			public byte[] generateSalt(int lengthBytes) {
				if (lengthBytes > 1024) {
					throw new IllegalArgumentException("length must be less than or equal to 1024");
				}
				return StringUtils.left(SALT_CHARS, lengthBytes).getBytes();
			}

			@Override
			public boolean includePlainSaltInEncryptionResults() {
				return false;
			}
    	};
    }
    
    @Bean
    public Hasher hasher(
    		@Value("${jasypt.hasher.pool.size:10}") final Integer poolSize, 
    		@Autowired SaltGenerator saltGenerator
    	) {
    	return new JasyptHasher(poolSize, saltGenerator);
    }
    
    @Bean
    public RestTemplate restTemplate() {
    	return new RestTemplate();
    }
    
	public static void main(final String[] args) {
		SpringApplication.run(SharedHashEncryptApiApp.class, args);
	}
}
