package com.gds.session;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository interface for Picker Session DAO operations
 * 
 * @author Rajapriya
 *
 */
public interface IPickerSessionRepository extends JpaRepository<PickerSession, Long> {

	@Query("SELECT new com.gds.session.PickerSessionDTO(sm.sessionId as sessionId, sm.sessionName as sessionName, "
			+ "sm.sessionStatus as sessionStatus, sm.eventDate as eventDate, sm.createdBy as createdBy, "
			+ "uc.joinDate as userJoinDate, sm.selectedRestaurant as selectedRestaurant) FROM PickerSession sm, Choice uc "
			+ "WHERE uc.userId = ?1 and sm.sessionId = uc.sessionId order by sm.startDate desc")
	List<PickerSessionDTO> findDTOByUserId(String userId); 	

}
