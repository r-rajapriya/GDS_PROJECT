package com.gds.choice;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gds.session.IPickerSessionService;
import com.gds.session.PickerSession;
import com.gds.user.IUserService;
import com.gds.user.User;
import com.gds.util.AppConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ChoiceController {
	
	@Autowired  
	private IChoiceService choiceService; 
	
	@Autowired
	private IUserService userService;
	
	@Autowired  
	private IPickerSessionService pickerSessionService; 
	
	@RequestMapping(value = "/choice/submitChoice", method = RequestMethod.POST)
    public ModelAndView submitChoice(String restaurantName, long sessionId, HttpServletRequest request) 
	{
		System.out.println("inside POST submitChoice - "+restaurantName+" for sess -" +sessionId);	
		ModelAndView modelAndView = new ModelAndView("redirect:/session/viewSession?sessionId="+sessionId);
		try
		{			       
	        User loginUser = (User) request.getSession().getAttribute(AppConstants.LOGIN_USER);
	        
			Choice aChoice = choiceService.findByChoiceId(loginUser.getUserId()+AppConstants.CHOICE_ID_CHAR+String.valueOf(sessionId)); 
			if(aChoice.getJoinDate() == null) 
				aChoice.setJoinDate(new Date());
			aChoice.setRestaurantChoice(restaurantName);
			aChoice.setSubmitDate(new Date());
			System.out.println("Choice before = "+aChoice);

			aChoice = choiceService.save(aChoice);
			System.out.println("Saved Choice after = "+aChoice);
			
			modelAndView.addObject(AppConstants.MSG, "Your Choice got submitted successfully");		    
		}
		catch(Exception ex)
		{
		    modelAndView.addObject(AppConstants.ERR_MSG, "Error while submit choice");
			System.out.println("Unable to submit choice"+ex.getMessage());
		}		
        return modelAndView;
    } 
	
	@RequestMapping(value = "/choice/inviteAll", method = RequestMethod.POST)
    public ModelAndView inviteAll(long sessionId, HttpServletRequest request) 
	{
		System.out.println("inside inviteAll for sess -" +sessionId);	
		ModelAndView modelAndView = new ModelAndView("redirect:/session/viewSession?sessionId="+sessionId);
		try
		{			       
	        User loginUser = (User) request.getSession().getAttribute(AppConstants.LOGIN_USER);
	        
	       //Create choices for other team members 
			List<User> userList = userService.findAllUsers();
			List<Choice> choiceList = choiceService.saveChoices(userList, sessionId, loginUser.getUserId(), true, false);
			System.out.println("Created Choice count = "+choiceList.size());			
			
			// Update invited flag in session object
			PickerSession pickerSession = pickerSessionService.findBySessionId(sessionId);
			pickerSession.setInviteAll(AppConstants.INVITE_ALL_STATUS);
			pickerSessionService.savePickerSession(pickerSession);
	        		
			modelAndView.addObject(AppConstants.MSG, "Other users are invited successfully");		    
		}
		catch(Exception ex)
		{
		    modelAndView.addObject(AppConstants.ERR_MSG, "Error while invite others");
		}		
        return modelAndView;
    } 
}
