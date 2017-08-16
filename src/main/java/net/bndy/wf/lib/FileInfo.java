package net.bndy.wf.lib;

import java.util.Arrays;

public class FileInfo {

	private String name;
	private String path;
	private String relativePath;
	private String extensionName;
	private long size;
	private FileType type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getExtensionName() {
		return extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public FileType getType() {
		if (this.type != null) {
			return FileInfo.getTypeByName(this.getName());
		}
		return this.type;
	}

	public void setType(FileType type) {
		this.type = type;
	}

	public static final String[] extensionNamesForImage = { ".bmp", ".gif", ".img", ".jpe", ".jpg", ".jpeg", ".pbm",
			".png", ".tga", ".tiff" };
	public static final String[] extensionNamesForVideo = { ".avi", ".flv", ".wmv", ".mov", ".mp4" };
	public static final String[] extensionNamesForAudio = { ".mp3", ".mav", ".aiff", ".wma" };

	public static FileType getTypeByName(String name) {
		String extensionName = name.toLowerCase().substring(name.indexOf("."));
		if (Arrays.asList(FileInfo.extensionNamesForImage).indexOf(extensionName) >= 0) {
			return FileType.IMAGE;
		} else if (Arrays.asList(FileInfo.extensionNamesForVideo).indexOf(extensionName) >= 0) {
			return FileType.VIDEO;
		} else if (Arrays.asList(FileInfo.extensionNamesForAudio).indexOf(extensionName) >= 0) {
			return FileType.AUDIO;
		}

		return FileType.UNKNOWN;
	}
}
