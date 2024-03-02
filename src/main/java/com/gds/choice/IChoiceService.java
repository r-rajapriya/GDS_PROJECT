package com.gds.choice;

/**
 * Service interface for Choice related functions
 * 
 * @author Rajapriya
 *
 */
public interface IChoiceService {
	Choice submitChoice(String userId, long sessionId, String restaurantName);
}
