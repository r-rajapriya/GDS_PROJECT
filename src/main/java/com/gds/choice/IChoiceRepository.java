package com.gds.choice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IChoiceRepository extends JpaRepository<Choice, Integer> {
	Choice findByChoiceId(String choiceId);
	//Map<String, Date> findBySessionId(long sessionId); 
	//Choice save(Choice choice); 
	//List<Choice> save(List<Choice> choiceList);
}
