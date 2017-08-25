package net.bndy.wf.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
	private static final String LOGIN_URI = "/api/user/login";
	private static final String SESSION_KEY = "USER";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if (!request.getMethod().equalsIgnoreCase("OPTIONS")) {		// pass pre-flight requests in Cross-origin in AJAX
			String uri = request.getRequestURI();
			if(!uri.toLowerCase().endsWith(LOGIN_URI)) {
				Object user = request.getSession().getAttribute(SESSION_KEY);
				if(user == null){
					return false;
				}
			}
		}
		return true;
	}

}
