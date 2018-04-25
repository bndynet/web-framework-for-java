/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.config;

import net.bndy.lib.StringHelper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@ConfigurationProperties(prefix="application")
public class ApplicationConfig {

	private String name;
	private String version;
	private String shortName;
	private String buildNumber;
	private String defaultLang;
	private boolean uploadDisabled;
	private String uploadPath;
	private String fullTextIndexPath;
	private String mailSender;
	private String allowedOrigins;
	private String defaultUserAvatar;
	private boolean renameUploadFile;
	private String adminSkin;
	private String adminRole;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDefaultLang() {
		return defaultLang;
	}

	public void setDefaultLang(String lang) {
		this.defaultLang = lang;
	}

	public String getAllowedOrigins() {
		return allowedOrigins;
	}

	public void setAllowedOrigins(String allowedOrigins) {
		this.allowedOrigins = allowedOrigins;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public String getFullTextIndexPath() {
		return fullTextIndexPath;
	}

	public boolean isRenameUploadFile() {
		return renameUploadFile;
	}

	public void setRenameUploadFile(boolean renameUploadFile) {
		this.renameUploadFile = renameUploadFile;
	}

	public String getMailSender() {
		return mailSender;
	}

	public void setMailSender(String mailSender) {
		this.mailSender = mailSender;
	}

	public String getDefaultUserAvatar() {
		return defaultUserAvatar;
	}

	public void setDefaultUserAvatar(String defaultUserAvatar) {
		this.defaultUserAvatar = defaultUserAvatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getAdminSkin() {
		return adminSkin;
	}

	public void setAdminSkin(String adminSkin) {
		this.adminSkin = adminSkin;
	}

	public String[] getAdminRole() {
		Assert.notNull(adminRole, "You did NOT set admin-role value in application.yml.");
		return StringHelper.splitWithoutWhitespace(adminRole, ",");
	}

	public void setAdminRole(String adminRole) {
		this.adminRole = adminRole;
	}

	public boolean isUploadDisabled() {
		return uploadDisabled;
	}

	public void setUploadDisabled(boolean uploadDisabled) {
		this.uploadDisabled = uploadDisabled;
	}

    public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

}
