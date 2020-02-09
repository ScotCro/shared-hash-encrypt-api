/*
 * SPDX-License-Identifier: MIT
 * This file is under MIT license.
 * 
 * Copyright (c) 2020 ScotCro LLC. All rights reserved.
 */
package com.scotcro.sharedhashencryptapi.entity;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Steve Willis, Employee of ScotCro LLC
 */
@Entity
@Table(name = "client_api_keys")
public class ClientApiEntity implements Serializable {
    private static final long serialVersionUID = -2629503414606908957L;

    @Id
    @Column(name = "api_key", length = 255)
    private String apiKey;

    @Column(name = "issued_to", length = 45, nullable = false)
    private String issuedTo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "effective_start_date", nullable = false)
    private Date effectiveStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "effective_end_date", nullable = true)
    private Date effectiveEndDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "created_by", length = 50, nullable = false)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private Date updated;

    @Column(name = "updated_by")
    private String updatedBy;

    public String getApiKey () {
        return apiKey;
    }

    public void setApiKey (final String apiKey) {
        this.apiKey = apiKey;
    }

    public String getIssuedTo () {
        return issuedTo;
    }

    public void setIssuedTo (final String issuedTo) {
        this.issuedTo = issuedTo;
    }

    public Date getEffectiveStartDate () {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate (final Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Date getEffectiveEndDate () {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate (final Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public Date getCreated () {
        return created;
    }

    public void setCreated (final Date created) {
        this.created = created;
    }

    public String getCreatedBy () {
        return createdBy;
    }

    public void setCreatedBy (final String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdated () {
        return updated;
    }

    public void setUpdated (final Date updated) {
        this.updated = updated;
    }

    public String getUpdatedBy () {
        return updatedBy;
    }

    public void setUpdatedBy (final String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @PrePersist
    public void prePersist() {
        if (created == null) {
            final Calendar cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getTimeZone("America/New_York"));
            created = cal.getTime();
        }
        if (StringUtils.isBlank(createdBy)) {
            createdBy = "SYSTEM";
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (updated == null) {
            final Calendar cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getTimeZone("America/New_York"));
            updated = cal.getTime();
        }
        if (StringUtils.isBlank(createdBy)) {
            updatedBy = "SYSTEM";
        }
    }
}
