package net.bndy.wf.test;

import org.junit.Assert;
import org.junit.Test;

import net.bndy.wf.lib.StringHelper;
import net.bndy.wf.lib.HttpHelper;

public class UtilsTest extends _Test {
	
	@Test
	public void generateRandomString() {
		System.out.println(StringHelper.generateRandomString(11));
		System.out.println(StringHelper.generateUUID());
		Assert.assertEquals(StringHelper.generateRandomString(10).length(), 10);
	}
	
	@Test
	public void httpGet() throws Exception {
		String s = HttpHelper.requestGet("http://www.hashcollision.org/hkn/python/idle_intro/index.html");
		System.out.println(s);
		Assert.assertTrue(s.indexOf("<!DOCTYPE") == 0);
	}
	
	@Test
	public void httpPost() throws Exception {
		// TODO
	}
}
