package com.gds.session;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for Picker Session functions
 * 
 * @author Rajapriya
 *
 */
@Service
public class PickerSessionServiceImpl implements IPickerSessionService {
	
	@Autowired 
	private IPickerSessionRepository pickerSessionRepository;
	
	/**
	 * Method to fetch all the sessions in which given user is involved
	 */
	@Override  
	public List<PickerSessionDTO> findAllUserSessions(String userId)  
	{ 
		//List<PickerSession> listSessions = pickerSessionRepository.findAllByUserId(userId);
		List<PickerSessionDTO> listSessions = pickerSessionRepository.findDTOByUserId(userId);
	    return listSessions;
	}
	
	/**
	 * Method to persist the Picker Session object
	 */
	@Override
	public PickerSession savePickerSession(PickerSession pickerSession) {
		pickerSession = pickerSessionRepository.save(pickerSession); 			
		return pickerSession;
	}

	/**
	 * Method to find Picker session by session id
	 */
	@Override
	public PickerSession findBySessionId(long sessionId) {
		Optional<PickerSession> pkOpt = pickerSessionRepository.findById(sessionId);		
		return pkOpt.get();
	}	
 }
