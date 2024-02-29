package com.gds.choice;

import java.util.List;

import com.gds.user.User;

public interface IChoiceService {
	Choice findByChoiceId(String choiceId); 
	Choice save(Choice choice); 
	List<Choice> saveChoices(List<User> userList, long sessionId, String createdByUserId, boolean inviteAll, boolean includeCreator);
	
}
