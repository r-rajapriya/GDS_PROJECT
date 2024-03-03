package com.gds.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.gds.user.User;
import com.gds.util.AppConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interceptor to validate the login user session
 * 
 * @author Rajapriya
 *
 */
@Component
public class RequestInterceptor implements HandlerInterceptor {

	private static final Logger log = LogManager.getLogger(RequestInterceptor.class);
	
	@Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
		 
		log.info("inside preHandle method");
		
		String reqUri = request.getRequestURI();
		if (("/gds/welcome").equalsIgnoreCase(reqUri) || ("/gds/processLogin").equalsIgnoreCase(reqUri) || ("/gds/error").equalsIgnoreCase(reqUri) 
				|| ("/gds/logout").equalsIgnoreCase(reqUri) || reqUri.startsWith("/gds/script/") || reqUri.startsWith("/gds/css")) 
			return true;	        
		else if (reqUri.startsWith("/gds/")) 
		{
		    User loginUser = (User) request.getSession().getAttribute(AppConstants.LOGIN_USER);	 
		    if (loginUser != null) 
		    {
				log.info("=== Found valid user session ===");
	        	return true;
		    }
		}
		response.sendRedirect("/gds/error");
		log.error("=== Invalid user session ===");
		return false;
	}
}
