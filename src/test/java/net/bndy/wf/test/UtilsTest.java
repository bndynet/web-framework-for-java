package net.bndy.wf.test;

import org.junit.Assert;
import org.junit.Test;

import net.bndy.wf.lib.Utils;

public class UtilsTest extends _Test {
	
	@Test
	public void generateRandomString() {
		System.out.println(Utils.generateRandomString(11));
		System.out.println(Utils.generateUUID());
		Assert.assertEquals(Utils.generateRandomString(10).length(), 10);
	}
}
