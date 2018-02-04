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
@Table(name="cms_attachment")
public class Attachment extends _BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private long boId;
	@Enumerated(value = EnumType.ORDINAL)
	private BoType boType;
	private String path;
	private String fileName;
	private String extensionName;
	
	@Enumerated(EnumType.ORDINAL)
	private FileType fileType;

	public long getBoId() {
		return boId;
	}

	public void setBoId(long boId) {
		this.boId = boId;
	}

	public BoType getBoType() {
		return boType;
	}

	public void setBoType(BoType boType) {
		this.boType = boType;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getExtensionName() {
		return extensionName;
	}
	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}
	public FileType getFileType() {
		return fileType;
	}
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
}
