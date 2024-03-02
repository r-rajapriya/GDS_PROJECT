package com.gds.session;

import java.util.List;

import com.gds.choice.Choice;

/**
 * Service interface for Picker Session functions
 * 
 * @author Rajapriya
 *
 */
public interface IPickerSessionService {
	List<PickerSessionDTO> findAllUserSessions(String createdBy);
	PickerSession createPickerSession(PickerSession pickerSession, String loginUserId); 
	PickerSession findBySessionId(long sessionId);
	PickerSession endPickerSession(long sessionId) throws Exception; 
	List<Choice> inviteUsers(long sessionId, String createdByUserId, boolean includeCreator);
}
