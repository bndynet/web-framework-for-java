/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="application")
public class ApplicationConfig {

	private String appLogo;
	private String appName;
	private String appShortName;
	private String uploadPath;
	private String uploadPathUrl;
	private String mailSender;
	private String allowedOrigins;
	private String defaultUserAvatar;
	private boolean renameUploadFile;

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

	public String getUploadPathUrl() {
		return uploadPathUrl;
	}
	
	public String getUploadPathUrl(String relativePath) {
		return this.getUploadPathUrl() + relativePath;
	}

	public void setUploadPathUrl(String uploadPathUrl) {
		this.uploadPathUrl = uploadPathUrl;
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

	public String getAppLogo() {
		return appLogo;
	}

	public void setAppLogo(String appLogo) {
		this.appLogo = appLogo;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppShortName() {
		return appShortName;
	}

	public void setAppShortName(String appShortName) {
		this.appShortName = appShortName;
	}
}
