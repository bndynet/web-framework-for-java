package net.bndy.wf;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.bndy.wf.lib.AppException;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Object defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		logger.error("{} for request {} from {}", e.getMessage(), req.getRequestURL(), req.getRemoteHost());
		return new AppException(e);
	}
}
