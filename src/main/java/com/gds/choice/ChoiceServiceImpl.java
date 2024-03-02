package com.gds.choice;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gds.util.AppConstants;

/**
 * Service class for Choice related functions
 * 
 * @author Rajapriya
 *
 */
@Service
public class ChoiceServiceImpl implements IChoiceService {
	
	private static final Logger log = LogManager.getLogger(ChoiceServiceImpl.class);

	@Autowired 
	private IChoiceRepository choiceRepository;
	
	/**
	 * Method to submit user choice of Restaurant name
	 */
	@Override
	public Choice submitChoice(String userId, long sessionId, String restaurantName) 
	{
		//Get the Choice details for the respective user
		String choiceId = String.valueOf(sessionId)+AppConstants.CHOICE_ID_CHAR+userId;
		log.info("choiceId = "+choiceId);
		Choice aChoice = choiceRepository.findByChoiceId(choiceId); 
		
		if(aChoice != null)
		{
			if(aChoice.getJoinDate() == null) 
				aChoice.setJoinDate(new Date());
			
			//Below check is required for this same method to be used for join session flow
			if(restaurantName != null && !"".equals(restaurantName)) 
			{
				aChoice.setRestaurantChoice(restaurantName);
				aChoice.setSubmitDate(new Date());
			}
			aChoice = choiceRepository.save(aChoice);
			log.info("Saved Choice after = "+aChoice);
		}			
		return aChoice;
	}

}
