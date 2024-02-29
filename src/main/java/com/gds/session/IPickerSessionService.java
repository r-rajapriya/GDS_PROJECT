package com.gds.session;

import java.util.List;

import com.gds.user.User;

/**
 * Service interface for Picker Session functions
 * 
 * @author Rajapriya
 *
 */
public interface IPickerSessionService {
	List<PickerSessionDTO> findAllUserSessions(String createdBy);
	PickerSession savePickerSession(PickerSession pickerSession); 
	PickerSession findBySessionId(long sessionId);
}
