package com.gds.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gds.util.AppConstants;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller class for User login related services
 * 
 * @author Rajapriya 
 *
 */
@Controller
public class UserController {
	
	private static final Logger log = LogManager.getLogger(UserController.class);
	
	@Autowired
	private IUserService userService;

	/**
	 * Method to show the home page
	 * @return
	 */
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String home() {
		return AppConstants.VIEW_LOGIN;
	}
	
	/**
	 * 
	 * Method to validate the user entered user name and password
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/processLogin", method = RequestMethod.POST)
	public ModelAndView processLogin(User user, HttpServletRequest request) {
		log.info("inside processLogin method"); 
		ModelAndView view = null;
		
		try
		{
			User loginUser = userService.validateUserLogin(user);
			request.getSession().setAttribute(AppConstants.LOGIN_USER, loginUser);				
			view = new ModelAndView("redirect:/session/listSessions");
		}
		catch (Exception exp) {
			view = new ModelAndView(AppConstants.VIEW_LOGIN);
			view.addObject(AppConstants.ERR_MSG, exp.getMessage());			
		}		
		return view;
	}
	
	/**
	 * Method to handle user logout
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/logout")
	 public ModelAndView logout(HttpServletRequest request) {
		  log.info("Logging out.....");
		  request.getSession().removeAttribute(AppConstants.LOGIN_USER);	
		  request.getSession().invalidate();
		  return new ModelAndView ("redirect:/welcome");
	}
}
