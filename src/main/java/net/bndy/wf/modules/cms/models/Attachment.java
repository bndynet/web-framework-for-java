/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.cms.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import net.bndy.wf.lib.FileType;
import net.bndy.wf.lib._BaseEntity;

@Entity
@Table(name = "cms_attachment")
public class Attachment extends _BaseEntity {

	private static final long serialVersionUID = 1L;

	private long boId;
	private long boTypeId;

	private String extensionName;

	private String fileName;

	@Enumerated(EnumType.ORDINAL)
	private FileType fileType;
	private String path;
	
	
	public long getBoId() {
		return boId;
	}
	public long getBoTypeId() {
		return boTypeId;
	}

	public String getExtensionName() {
		return extensionName;
	}

	public String getFileName() {
		return fileName;
	}

	public FileType getFileType() {
		return fileType;
	}

	public String getPath() {
		return path;
	}

	public void setBoId(long boId) {
		this.boId = boId;
	}

	public void setBoTypeId(long boTypeId) {
		this.boTypeId = boTypeId;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
