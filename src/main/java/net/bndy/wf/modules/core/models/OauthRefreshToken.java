/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core.models;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.bndy.wf.lib._BaseEntity;

import java.sql.Blob;

@Entity
@Table(name = "oauth_refresh_token")
public class OauthRefreshToken extends _BaseEntity {
    private String tokenId;
    private Blob token;
    private Blob authentication;
}
