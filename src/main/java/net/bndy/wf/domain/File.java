package net.bndy.wf.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class File extends _BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private long boTypeId;
	private String path;
	private String fileName;
	private String extensionName;
	@Enumerated(EnumType.ORDINAL)
	private FileType fileType;

	public long getBoTypeId() {
		return boTypeId;
	}
	public void setBoTypeId(long boTypeId) {
		this.boTypeId = boTypeId;
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
