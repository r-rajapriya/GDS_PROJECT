package com.gds.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gds.choice.Choice;
import com.gds.choice.IChoiceRepository;
import com.gds.user.IUserService;
import com.gds.user.User;
import com.gds.util.AppConstants;

/**
 * Service class for Picker Session functions
 * 
 * @author Rajapriya 
 *
 */
@Service
public class PickerSessionServiceImpl implements IPickerSessionService {
	
	private static final Logger log = LogManager.getLogger(PickerSessionServiceImpl.class);
	
	@Autowired 
	private IPickerSessionRepository pickerSessionRepository;
	
	@Autowired  
	private IUserService userService;
	
	@Autowired 
	private IChoiceRepository choiceRepository;
	
	/**
	 * Method to fetch all the sessions in which given user is involved
	 */
	@Override  
	public List<PickerSessionDTO> findAllUserSessions(String userId)  
	{ 
		List<PickerSessionDTO> listSessions = pickerSessionRepository.findDTOByUserId(userId);
	    return listSessions;
	}
	
	/**
	 * Method to create new Picker Session along with the Choices
	 */
	@Override
	public PickerSession createPickerSession(PickerSession pickerSession, String loginUserId) 
	{
		// Checks the invite_all check box value first
		boolean inviteAll = AppConstants.INVITE_ALL_STATUS.equals(pickerSession.getInviteAll());
		log.info("inviteAll - "+inviteAll);
		
		//Create new session 	        
		pickerSession.setSessionStatus(AppConstants.SESSION_STATUS_OPEN);		
		pickerSession.setCreatedBy(loginUserId);
		pickerSession.setStartDate(new Date());	
		pickerSession = pickerSessionRepository.save(pickerSession);
		
		//Create choices for initiator and other team members 
		if(inviteAll)
		{
			log.info("inside if cond inviteAll");
			List<Choice> choiceList = inviteUsers(pickerSession.getSessionId(), pickerSession.getCreatedBy(), true);
			log.info("Created Choice count = "+choiceList.size());
		}
		else //Create Choice only for the initiator
		{
			log.debug("inside else cond inviteAll");
			Choice choice = new Choice((String.valueOf(pickerSession.getSessionId())+AppConstants.CHOICE_ID_CHAR+loginUserId), 
					loginUserId, pickerSession.getSessionId(), new Date());
			choiceRepository.save(choice);
		}					
		return pickerSession;
	}
	
	/**
	 * Method to create choices for all team members
	 */
	@Override
	public List<Choice> inviteUsers(long sessionId, String createdByUserId, boolean includeCreator) {
		log.info("inside inviteUsers method");
		
		//Fetch all team members 
		List<User> userList = userService.findAllUsers();
		
		//Create choices for other team members and save 
		List<Choice> choiceList = createChoices(userList, sessionId, createdByUserId, includeCreator);		
		choiceList = choiceRepository.saveAll(choiceList);	
		
		// Update invited flag in picker session object
		PickerSession pickerSession = findBySessionId(sessionId);
		pickerSession.setInviteAll(AppConstants.INVITE_ALL_STATUS);
		pickerSessionRepository.save(pickerSession);
				
		return choiceList;
	}

	/**
	 * Create choices for initiator and other team members if required
	 * 
	 * @param userList
	 * @param SessionId
	 * @param loginUserId
	 * @param inviteAll
	 * @return
	 */
	private List<Choice> createChoices(List<User> userList, long sessionId, String createdByUserId, boolean includeCreator)
	{
		log.info("inside createChoices method ");

		List<Choice> choiceList = new ArrayList<Choice>();
		Choice choice = new Choice();		

		for(User anUser : userList)
		{
			if(createdByUserId.equalsIgnoreCase(anUser.getUserId()))
			{
				if(includeCreator) //From Session creation - invite all check box function
					choice = new Choice((String.valueOf(sessionId)+AppConstants.CHOICE_ID_CHAR+anUser.getUserId()), anUser.getUserId(), sessionId, new Date());
			}					
			else //From View Session - invite all button function
				choice = new Choice((String.valueOf(sessionId)+AppConstants.CHOICE_ID_CHAR+anUser.getUserId()), anUser.getUserId(), sessionId, null);
			choiceList.add(choice);
		}				
		return choiceList;
	}

	
	/**
	 * Method to find Picker session by session id
	 */
	@Override
	public PickerSession findBySessionId(long sessionId) {
		Optional<PickerSession> pkOpt = pickerSessionRepository.findById(sessionId);		
		return pkOpt.get();
	}	
	
	/**
	 * Method to End the Picker Session object
	 * @throws Exception 
	 */
	@Override
	public PickerSession endPickerSession(long sessionId) throws Exception 
	{
		log.info("endPickerSession method");

    	//Gets the session object based on session id
		PickerSession pickerSession = findBySessionId(sessionId);
		
		//Randomly choose the Restaurant name based on user choices
		String restName = selectRestaurant(pickerSession.getUserChoices());
		if(restName != null && !"".equals(restName))
		{
			//Update the session object with the selected restaurant name
			pickerSession.setSelectedRestaurant(restName);
			pickerSession.setSessionStatus(AppConstants.SESSION_STATUS_END);		
			pickerSession.setEndDate(new Date());
			pickerSession = pickerSessionRepository.save(pickerSession);
			log.info("Final pickerSession - "+pickerSession);
		}
		else
			throw new Exception("Restaurant name list is empty");
		return pickerSession;
	}
	
	/**
	 * Method to randomly choose the Restaurant name based on user submitted choices  
	 * 
	 * @param choiceList
	 * @return
	 */
	private String selectRestaurant(List<Choice> choiceList)
	{
		log.info("selectRestaurant method");
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
			log.info("Randomly selected Restaurant - "+selRestName);
		}	
		return selRestName;
	}
 }
