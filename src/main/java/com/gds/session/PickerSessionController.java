package com.gds.session;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gds.choice.Choice;
import com.gds.choice.IChoiceService;
import com.gds.user.IUserService;
import com.gds.user.User;
import com.gds.util.AppConstants;

import jakarta.servlet.http.HttpServletRequest;

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
	
	@Autowired
	private IUserService userService;
	
	@Autowired  
	private IChoiceService choiceService; 	

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
    public ModelAndView createSession(HttpServletRequest request, PickerSession pickerSession) 
	{
		log.info("inside createSession method");
		ModelAndView modelAndView = new ModelAndView(AppConstants.VIEW_SESSIONS);
		
		try
		{	
			// Checks the invite_all check box value first
			boolean inviteAll = AppConstants.INVITE_ALL_STATUS.equals(pickerSession.getInviteAll());
			User loginUser = (User) request.getSession().getAttribute(AppConstants.LOGIN_USER);
			
			//Create new session 	        
			pickerSession.setSessionStatus(AppConstants.SESSION_STATUS_OPEN);		
			pickerSession.setCreatedBy(loginUser.getUserId());
			pickerSession.setStartDate(new Date());	
			pickerSession = pickerSessionService.savePickerSession(pickerSession);
			
			//Create choices for initiator and other team members if required
			List<User> userList = userService.findAllUsers();
			List<Choice> choiceList = choiceService.saveChoices(userList, pickerSession.getSessionId(), pickerSession.getCreatedBy(), inviteAll, true);
			log.info("Created Choice count = "+choiceList.size());
						
		    modelAndView.addObject(AppConstants.MSG, "New Session got initiated successfully");		    	
		    modelAndView.addObject(AppConstants.REQOBJ_SESSION_LIST, pickerSessionService.findAllUserSessions(pickerSession.getCreatedBy()));
		    modelAndView.addObject(AppConstants.REQOBJ_SESSION, new PickerSession());
		}
		catch(Exception ex)
		{
		    modelAndView.addObject(AppConstants.ERR_MSG, "Error while initiate session");
		    log.error("Unable to create session : "+ex.getMessage());
		}		
        return modelAndView;
    } 
	
	/**
	 * Service method for a team member to join the session
	 * 
	 * @param request
	 * @param sessionId
	 * @return
	 */
	@RequestMapping(value = "/session/joinSession", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView joinPickerSession(HttpServletRequest request, @RequestParam("sessionId") long sessionId)   
	{  
		log.info("inside joinPickerSession method - "+sessionId);	
	    ModelAndView modelAndView = new ModelAndView(AppConstants.VIEW_SESSIONS);

	    try
	    {
			User loginUser = (User) request.getSession().getAttribute(AppConstants.LOGIN_USER);
			
			//Get the respective Choice object
			Choice choice = choiceService.findByChoiceId(loginUser.getUserId()+AppConstants.CHOICE_ID_CHAR+String.valueOf(sessionId));
			choice.setJoinDate(new Date());
			choice = choiceService.save(choice);
			
		    modelAndView.addObject(AppConstants.CHOICE_MSG, "Joined the selected session, you can click on Session Name to submit your choice.");		    	
		    modelAndView.addObject(AppConstants.REQOBJ_SESSION_LIST, pickerSessionService.findAllUserSessions(loginUser.getUserId()));
		    modelAndView.addObject(AppConstants.REQOBJ_SESSION, new PickerSession());
	    }
	    catch(Exception ex)
		{
		    modelAndView.addObject(AppConstants.ERR_MSG, "Error while join session");
		    log.error("Unable to join the session : "+ex.getMessage());
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
	public ModelAndView getPickerSession(HttpServletRequest request, @RequestParam("sessionId") long sessionId)   
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
	public ModelAndView endPickerSession(long sessionId)   
	{  
		log.info("inside endPickerSession method - "+sessionId);
	    ModelAndView modelAndView = new ModelAndView(AppConstants.VIEW_CHOICES);
	    
	    try
	    {
	    	//Gets the session object based on session id
			PickerSession pickerSession = pickerSessionService.findBySessionId(sessionId);
			
			//Randomly choose the Restaurant name based on user choices
			String restName = selectRestaurant(pickerSession.getUserChoices());
			if(restName != null && !"".equals(restName))
			{
				pickerSession.setSessionStatus(AppConstants.SESSION_STATUS_END);		
				pickerSession.setSelectedRestaurant(restName);
				pickerSession.setEndDate(new Date());
				pickerSession = pickerSessionService.savePickerSession(pickerSession);
				
			    modelAndView.addObject(AppConstants.MSG, "Restaurant is selected randomly");
			}
			else
			    modelAndView.addObject(AppConstants.ERR_MSG, "Unable to choose Restaurant name");

		    modelAndView.addObject(AppConstants.REQOBJ_SESSION, pickerSession);
	    }
	    catch(Exception ex)
		{
		    log.error("Unable to choose the Restaurant : "+ex.getMessage());
		}	
	    return modelAndView;		 
	} 
	
	/**
	 * Method to randomly choose the Restaurant name based on user submitted choices  
	 * 
	 * @param choiceList
	 * @return
	 */
	private String selectRestaurant(List<Choice> choiceList)
	{
		String selRestName = null;
		
		//Filter the empty records
		choiceList = choiceList.stream()
				.filter(c -> (c.getRestaurantChoice() != null && !c.getRestaurantChoice().trim().equals("")))
				.toList(); 
		int finalCount = choiceList.size(); 
		log.info("count after filter the empty choices - "+finalCount);
		
		// Generate random number to pick the restaurant name
		if(finalCount > 0)
		{
			Random rand = new Random();	        
	        int index = rand.nextInt(finalCount);
			selRestName = choiceList.get(index).getRestaurantChoice();
		}		
		return selRestName;
	}
}
