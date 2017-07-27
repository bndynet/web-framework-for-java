package net.bndy.wf.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class Attachment extends _BaseEntity {

	private static final long serialVersionUID = 1L;

	private long boId;
	private String path;
	private String fileName;
	private String extensionName;
	@Enumerated(EnumType.ORDINAL)
	private FileType fileType;
	
	public FileType getFileType() {
		return fileType;
	}
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	public long getBoId() {
		return boId;
	}
	public void setBoId(long boId) {
		this.boId = boId;
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
}
