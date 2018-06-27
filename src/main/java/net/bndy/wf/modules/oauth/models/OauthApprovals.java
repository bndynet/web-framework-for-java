/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.oauth.models;

import net.bndy.wf.lib._BaseEntity;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "oauth_approvals")
public class OauthApprovals extends _BaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(name = "userId")
    private String userId;
    @Column(name = "clientId")
    private String clientId;
    @Column(name = "scope")
    private String scope;
    @Column(name = "status")
    private String status;
    @Column(name = "expiresAt")
    private Timestamp expiresAt;
    @UpdateTimestamp
    @Column(name = "lastModifiedAt", columnDefinition = "TIMESTAMP NULL")
    private Date lastModifiedAt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Timestamp expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
