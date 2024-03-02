package com.gds.session;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gds.choice.Choice;
import com.gds.user.User;
import com.gds.util.AppConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * Controller class for Picker Session related services
 * 
 * @author Rajapriya
 *
 */
@Controller
public class PickerSessionController {
	
	private static final Logger log = LogManager.getLogger(PickerSessionController.class);

	@Autowired  
	private IPickerSessionService pickerSessionService; 
	
	/**
	 * Service method to fetch all the sessions based on login user id 
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/session/listSessions", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView getAllUserSessions(HttpServletRequest request)   
	{  
		log.info("inside getAllUserSessions method");
	    ModelAndView modelAndView = new ModelAndView(AppConstants.VIEW_SESSIONS);

	    try 
	    {
	    	User loginUser = (User) request.getSession().getAttribute(AppConstants.LOGIN_USER);
			List<PickerSessionDTO> listSessions = pickerSessionService.findAllUserSessions(loginUser.getUserId());	
			
		    modelAndView.addObject(AppConstants.REQOBJ_SESSION_LIST, listSessions);
	    }
	    catch(Exception ex)
		{
		    modelAndView.addObject(AppConstants.ERR_MSG, "Error while listing the sessions");
			log.error("Unable to list sessions : "+ex.getMessage());
		}	
	    return modelAndView;		 
	} 
	
	/**
	 * Service method to create new session in the system
	 * 
	 * @param request
	 * @param pickerSession
	 * @return
	 */
	@RequestMapping(value = "/session/createSession", method = RequestMethod.POST)
    public ModelAndView createSession(HttpServletRequest request, @Valid PickerSession pickerSession, BindingResult bindingResult) 
	{
		log.info("inside createSession method");
		ModelAndView modelAndView = new ModelAndView(AppConstants.VIEW_SESSIONS);
		User loginUser = (User) request.getSession().getAttribute(AppConstants.LOGIN_USER);

		try
		{
			if (bindingResult.hasErrors()) {
			    log.error("Encountered validation error : "+bindingResult.getAllErrors());
			    modelAndView.addObject(AppConstants.ERR_MSG, "Encountered validation error - "+bindingResult.getAllErrors().get(0).getDefaultMessage());
			}
			else
			{
				pickerSession = pickerSessionService.createPickerSession(pickerSession, loginUser.getUserId());
				
			    modelAndView.addObject(AppConstants.MSG, "New Session got initiated successfully");		    
			    modelAndView.addObject(AppConstants.REQOBJ_SESSION, new PickerSession());
			}			
		}
		catch(Exception ex)
		{
		    modelAndView.addObject(AppConstants.ERR_MSG, "Error while initiate session, try with different session name.");
		    log.error("Unable to create session : "+ex.getMessage());
		}		
		finally
		{
			modelAndView.addObject(AppConstants.REQOBJ_SESSION_LIST, pickerSessionService.findAllUserSessions(loginUser.getUserId()));
		}
        return modelAndView;
    } 
	
	/**
	 * Method to handle invite other team members function
	 * 
	 * @param sessionId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/session/inviteAll", method = RequestMethod.POST)
    public ModelAndView inviteAll(long sessionId, HttpServletRequest request) 
	{
		log.info("inside inviteAll for session -" +sessionId);	
		ModelAndView modelAndView = new ModelAndView(AppConstants.VIEW_CHOICES); //"redirect:/session/viewSession?sessionId="+sessionId);
		try
		{			       
	        User loginUser = (User) request.getSession().getAttribute(AppConstants.LOGIN_USER);	        
	        List<Choice> choiceList = pickerSessionService.inviteUsers(sessionId, loginUser.getUserId(), false);
			log.info("Created Choice count = "+choiceList.size());			
	        
			PickerSession pickerSession = pickerSessionService.findBySessionId(sessionId);
		    modelAndView.addObject(AppConstants.REQOBJ_SESSION, pickerSession);	 
			modelAndView.addObject(AppConstants.MSG, "Other users are invited successfully");		    
		}
		catch(Exception ex)
		{
		    modelAndView.addObject(AppConstants.ERR_MSG, "Error while invite others");
		}		
        return modelAndView;
    } 
	
	/**
	 * Service method to get the Picker Session based on session id
	 * 
	 * @param request
	 * @param sessionId
	 * @return
	 */
	@RequestMapping(value = "/session/viewSession", method = RequestMethod.GET)
	public ModelAndView viewSession(HttpServletRequest request, @RequestParam("sessionId") long sessionId)   
	{  
		log.info("inside getPickerSession method - "+sessionId);
	    ModelAndView modelAndView = new ModelAndView(AppConstants.VIEW_CHOICES);
		try
		{
			PickerSession pickerSession = pickerSessionService.findBySessionId(sessionId);
		    modelAndView.addObject(AppConstants.REQOBJ_SESSION, pickerSession);	 
		}
		catch(Exception ex)
		{
		    modelAndView.addObject(AppConstants.ERR_MSG, "Error while getting session details");
		    log.error("Unable to get the session details : "+ex.getMessage());
		}			   
	    return modelAndView;		
	}
	
	/**
	 * Service method to end the session by the creator user
	 * 
	 * @param sessionId
	 * @return
	 */
	@RequestMapping(value = "/session/endSession", method = RequestMethod.POST)
	public ModelAndView endSession(long sessionId)   
	{  
		log.info("inside endPickerSession method - "+sessionId);
	    ModelAndView modelAndView = new ModelAndView(AppConstants.VIEW_CHOICES);
	    
	    try
	    {
	    	PickerSession pickerSession = pickerSessionService.endPickerSession(sessionId);
			modelAndView.addObject(AppConstants.MSG, "Restaurant is selected randomly");
		    modelAndView.addObject(AppConstants.REQOBJ_SESSION, pickerSession);
	    }
	    catch(Exception ex)
		{
		    log.error("Unable to choose the Restaurant : "+ex.getMessage());
		    modelAndView.addObject(AppConstants.ERR_MSG, "Unable to choose Restaurant name");
		}	
	    return modelAndView;		
	} 
	
}
