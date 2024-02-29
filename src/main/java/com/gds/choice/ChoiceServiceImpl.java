package com.gds.choice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gds.user.User;

@Service
public class ChoiceServiceImpl implements IChoiceService {
	
	@Autowired 
	private IChoiceRepository choiceRepository;
	
	/*@Override
	public List<Choice> findBySessionId(int sessionId) {
		System.out.println("inside findBySessionId method");
		List<Choice> listChoices = choiceRepository.findBySessionId(sessionId);
	    return listChoices;
	}*/

	@Override
	public Choice save(Choice choice) {
		System.out.println("inside Choice save method");	
		choice = choiceRepository.save(choice);			
		return choice;
	}

	@Override
	public List<Choice> saveChoices(List<User> userList, long sessionId, String createdByUserId, boolean inviteAll, boolean includeCreator) {
		System.out.println("inside saveChoices method");	
		List<Choice> choiceList = createChoices(userList, sessionId, createdByUserId, inviteAll, includeCreator);		
		
		choiceList = choiceRepository.saveAll(choiceList);			
		return choiceList;
	}

	@Override
	public Choice findByChoiceId(String choiceId) {
		Choice aChoice = choiceRepository.findByChoiceId(choiceId);
		return aChoice;
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
	private List<Choice> createChoices(List<User> userList, long sessionId, String loginUserId, boolean inviteAll, boolean includeCreator)
	{
		List<Choice> choiceList = new ArrayList<Choice>();
		Choice choice = new Choice();
		
		System.out.println("createChoices method inviteAll = "+inviteAll);
		if(inviteAll)
		{
			for(User anUser : userList)
			{
				if(loginUserId.equalsIgnoreCase(anUser.getUserId()))
				{
					if(includeCreator)
						choice = new Choice((anUser.getUserId()+"-"+String.valueOf(sessionId)),anUser.getUserId(), sessionId, new Date());
				}					
				else
					choice = new Choice((anUser.getUserId()+"-"+String.valueOf(sessionId)),anUser.getUserId(), sessionId, null);
				choiceList.add(choice);
			}
		}
		else
		{
			choice = new Choice((loginUserId+"-"+String.valueOf(sessionId)),loginUserId, sessionId, new Date());
			choiceList.add(choice);
		}
		
		return choiceList;
	}
 }
