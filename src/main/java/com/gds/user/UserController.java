package com.gds.user;

import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gds.util.AppConstants;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	  private static final Logger log = LogManager.getLogger(UserController.class);

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String home() {
		return AppConstants.VIEW_LOGIN;
	}

	@RequestMapping(value = "/processLogin", method = RequestMethod.POST)
	public ModelAndView processLogin(User user, HttpServletRequest request) {
		log.info("inside processLogin");
		ModelAndView view = null;
		
		String usrId = "", pwd = "";
		try {
			if (user.getUserId() != null)
				usrId = user.getUserId();
			if (user.getPassword() != null)
				pwd = user.getPassword();			
	
			if (usrId.length() == 0) {
				view = new ModelAndView(AppConstants.LOGIN_USER);
				view.addObject(AppConstants.ERR_MSG, "User Id is invalid");
				return view;
			}
			
			// MD5 hashing the input password
			MessageDigest md = MessageDigest.getInstance("MD5");
		    md.update(pwd.getBytes());
		    byte[] digest = md.digest();
		    String hashedPwd = DatatypeConverter.printHexBinary(digest);
		    
		    log.info("Retrieving user details for : " + usrId);
			User loginUser = userService.findByUserId(usrId.toUpperCase(), hashedPwd);
			if (loginUser != null) {
				System.out.println("User login is SUCCESS for " + loginUser);
				request.getSession().setAttribute(AppConstants.LOGIN_USER, loginUser);				
				view = new ModelAndView("redirect:/session/listSessions");
				//view.addObject("loginUser", loginUser);				
				return view;
			}
			else {
				System.out.println("User login failed for " + usrId);
				view = new ModelAndView(AppConstants.VIEW_LOGIN);
				view.addObject(AppConstants.ERR_MSG, "Invalid Login credentials ");
				return view;
			}

		} catch (Exception exp) {
			view = new ModelAndView(AppConstants.VIEW_LOGIN);
			view.addObject(AppConstants.ERR_MSG, "Error while logging in. please try later ");
			exp.printStackTrace();
		}

		return view;
	}
	
	@RequestMapping(value="/logout")
	 public ModelAndView logout(HttpServletRequest request) {
		 System.out.println("Logging out.....");
		  request.getSession().invalidate();
		  return new ModelAndView ("redirect:/welcome");
		  
	}

	/*
	 * @RequestMapping("/error") public ModelAndView error(HttpServletRequest
	 * request, HttpServletResponse response) {
	 * logger.debug("IYP within login-error controller");
	 * request.setAttribute("errMsg", "Unknown Error. Please try later"); return new
	 * ModelAndView("error"); }
	 * 
	 * @RequestMapping("/CommonError") public ModelAndView
	 * CommonError(HttpServletRequest request, HttpServletResponse response) {
	 * logger.debug("IYP within login-error controller");
	 * request.setAttribute("errMsg", request.getParameter("errMsg")); return new
	 * ModelAndView("CommonError"); }
	 * 
	 * @RequestMapping(value="/logout") public ModelAndView
	 * logout(HttpServletRequest request, HttpServletResponse response) {
	 * logger.debug("IYP within LoginController.logout"); HttpSession session =
	 * request.getSession(); session.invalidate(); return new ModelAndView
	 * ("login");
	 * 
	 * }
	 */
}
