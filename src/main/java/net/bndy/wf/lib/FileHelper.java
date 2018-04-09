/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.lib;

import java.util.Arrays;

public class FileHelper {
	public static final String[] extensionNamesForImage = { ".bmp", ".gif", ".img", ".jpe", ".jpg", ".jpeg", ".pbm",
			".png", ".tga", ".tiff" };
	public static final String[] extensionNamesForVideo = { ".avi", ".flv", ".wmv", ".mov", ".mp4" };
	public static final String[] extensionNamesForAudio = { ".mp3", ".mav", ".aiff", ".wma" };

	public static FileType getTypeByName(String name) {
		String extensionName = name.toLowerCase().substring(name.indexOf("."));
		if (Arrays.asList(FileHelper.extensionNamesForImage).indexOf(extensionName) >= 0) {
			return FileType.IMAGE;
		} else if (Arrays.asList(FileHelper.extensionNamesForVideo).indexOf(extensionName) >= 0) {
			return FileType.VIDEO;
		} else if (Arrays.asList(FileHelper.extensionNamesForAudio).indexOf(extensionName) >= 0) {
			return FileType.AUDIO;
		}

		return FileType.UNKNOWN;
	}
}
