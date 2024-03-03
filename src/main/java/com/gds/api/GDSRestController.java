package com.gds.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.gds.choice.Choice;
import com.gds.choice.IChoiceService;
import com.gds.session.IPickerSessionService;
import com.gds.session.PickerSession;
import com.gds.user.User;
import com.gds.util.AppConstants;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Rest Controller class to process the rest call to submit user choice
 * 
 * @author Rajapriya
 *
 */
@RestController
public class GDSRestController {
	
	private static final Logger log = LogManager.getLogger(GDSRestController.class);

	@Autowired
	private IChoiceService choiceService;
	 
	@Autowired  
	private IPickerSessionService pickerSessionService; 
	
	/**
	 * Handler method to process submit choice function
	 * 	
	 * @param sessionId
	 * @param restaurantName
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/api/submitChoice", method = RequestMethod.POST) 
	public ResponseEntity<PickerSession> submitChoice(@RequestParam String sessionId, String restaurantName, HttpServletRequest request)
	{	
		log.info("inside API submitChoice method");	

		if(sessionId == null || "".equals(sessionId.trim()))
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Invalid session id");	
		
		if(restaurantName == null || "".equals(restaurantName.trim()))
			throw new ResponseStatusException(
			           HttpStatus.BAD_REQUEST, "Invalid restaurant name");	
		
		PickerSession pickerSession = null;
		try
		{				
	        User loginUser = (User) request.getSession().getAttribute(AppConstants.LOGIN_USER);
	        
	        Choice aChoice = choiceService.submitChoice(loginUser.getUserId(), Long.valueOf(sessionId), restaurantName);	
	        log.info("Saved Choice after = "+aChoice);
	        pickerSession = pickerSessionService.findBySessionId(Long.valueOf(sessionId));
	        request.setAttribute(AppConstants.CHOICE_MSG, "Your Choice got submitted successfully");	        			
		} 
		catch (Exception e) {
			request.setAttribute(AppConstants.ERR_MSG, "Error while submit choice");
			log.error("Unable to submit choice "+e.getMessage());
			throw new ResponseStatusException(
			           HttpStatus.INTERNAL_SERVER_ERROR, "Unable to submit choice", e);			
		}		
		return new ResponseEntity<PickerSession>(pickerSession, HttpStatus.OK);
	}
}
