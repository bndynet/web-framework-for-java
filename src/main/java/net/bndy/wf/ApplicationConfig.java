/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="application")
public class ApplicationConfig {
	
	private boolean renameUploadFile;
	private String uploadPath;
	private String uploadPathUrl;

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
}
