package net.bndy.wf.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.junit.Assert;

import net.bndy.wf.lib.IOHelper;
import net.bndy.wf.lib.StringHelper;

public class LibTest {
	
	@Test
	public void createThumbnail() throws IOException{
		IOHelper.createThumbnail("/Users/bendy/Downloads/logo.jpeg", 200, "/Users/bendy/Downloads/logo_s.jpeg");
	}
	
	@Test
	public void Base64() throws UnsupportedEncodingException {
		String s = "bndy.net";
		String e = StringHelper.encodeBase64(s);
		Assert.assertTrue(s.equals(StringHelper.decodeBase64(e)));
	}
}
