/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.lib;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;

public class Utils {

	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}
	
	public static String generateRandomString(int length) {
		SecureRandom secureRandom = new SecureRandom();
		byte[] token = new byte[length / 2];
		secureRandom.nextBytes(token);
		return new BigInteger(1, token).toString(16).toUpperCase();
	}
}
