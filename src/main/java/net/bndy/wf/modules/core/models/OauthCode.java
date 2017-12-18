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
@Table(name = "oauth_code")
public class OauthCode extends _BaseEntity {
    private String code;
    private Blob authentication;
}
