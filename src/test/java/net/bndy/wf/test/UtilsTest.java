package net.bndy.wf.test;

import org.junit.Assert;
import org.junit.Test;

import net.bndy.wf.lib.StringHelper;

public class UtilsTest extends _Test {
	
	@Test
	public void generateRandomString() {
		System.out.println(StringHelper.generateRandomString(11));
		System.out.println(StringHelper.generateUUID());
		Assert.assertEquals(StringHelper.generateRandomString(10).length(), 10);
	}
}
