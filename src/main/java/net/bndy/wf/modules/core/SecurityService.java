/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.modules.core;

public interface SecurityService {
	
	String findLoggedInUsername();
	void autologin(String username, String password);
}
