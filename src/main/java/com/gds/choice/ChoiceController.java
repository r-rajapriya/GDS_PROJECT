package com.gds.choice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gds.session.IPickerSessionService;
import com.gds.session.PickerSession;
import com.gds.user.User;
import com.gds.util.AppConstants;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller class for Choice related functions
 * 
 * @author Rajapriya
 *
 */
@Controller
public class ChoiceController {

	private static final Logger log = LogManager.getLogger(ChoiceController.class);

	@Autowired
	private IChoiceService choiceService;

	@Autowired
	private IPickerSessionService pickerSessionService;

	/**
	 * Service method for a team member to join the session
	 * 
	 * @param request
	 * @param sessionId
	 * @return
	 */
	@RequestMapping(value = "/choice/join", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView joinSession(HttpServletRequest request, @RequestParam("sessionId") long sessionId) {
		log.info("inside joinSession method - " + sessionId);
		ModelAndView modelAndView = new ModelAndView(AppConstants.VIEW_SESSIONS); // "redirect:/session/listSessions");

		try {
			User loginUser = (User) request.getSession().getAttribute(AppConstants.LOGIN_USER);

			// Submit Choice with join date as current date, 3rd parameter to be null for
			// join session scenario
			choiceService.submitChoice(loginUser.getUserId(), sessionId, null);

			modelAndView.addObject(AppConstants.CHOICE_MSG,
					"Joined the selected session, you can click on Session Name to submit your choice.");
			modelAndView.addObject(AppConstants.REQOBJ_SESSION_LIST,
					pickerSessionService.findAllUserSessions(loginUser.getUserId()));
			modelAndView.addObject(AppConstants.REQOBJ_SESSION, new PickerSession());
		} catch (Exception ex) {
			modelAndView.addObject(AppConstants.ERR_MSG, "Error while join session");
			log.error("Unable to join the session : " + ex.getMessage());
		}
		return modelAndView;
	}
}
