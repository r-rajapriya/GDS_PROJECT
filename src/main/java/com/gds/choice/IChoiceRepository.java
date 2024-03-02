package com.gds.choice;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Choice related DAO functions
 * 
 * @author Rajapriya
 *
 */
public interface IChoiceRepository extends JpaRepository<Choice, Integer> {
	Choice findByChoiceId(String choiceId);
}
