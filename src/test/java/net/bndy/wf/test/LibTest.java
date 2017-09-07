package net.bndy.wf.test;

import java.io.IOException;

import org.junit.Test;

import net.bndy.wf.lib.IOHelper;

public class LibTest {
	
	@Test
	public void createThumbnail() throws IOException{
		IOHelper.createThumbnail("/Users/bendy/Downloads/logo.jpeg", 200, "/Users/bendy/Downloads/logo_s.jpeg");
	}
}
