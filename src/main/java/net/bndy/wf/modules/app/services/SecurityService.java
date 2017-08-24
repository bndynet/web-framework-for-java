package net.bndy.wf.modules.app.services;

public interface SecurityService {
	
	String findLoggedInUsername();
	void autologin(String username, String password);
}
