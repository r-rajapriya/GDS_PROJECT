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
	/*@Query(value = "SELECT sm.session_id, sm.session_name, sm.event_date, sm.session_status, sm.created_by, sm.start_date,"
			+ "sm.end_date, sm.selected_restaurant, sm.invite_all FROM session_master sm, user_choice uc "
			+ "WHERE uc.user_id = ?1 and sm.session_id = uc.session_id order by sm.start_date desc", 
			nativeQuery = true)
	List<PickerSession> findAllByUserId(String userId); 	*/
	//, uc.join_date as userJoinDate
	
	@Query("SELECT new com.gds.session.PickerSessionDTO(sm.sessionId as sessionId, sm.sessionName as sessionName, "
			+ "sm.sessionStatus as sessionStatus, sm.eventDate as eventDate, sm.createdBy as createdBy, "
			+ "uc.joinDate as userJoinDate, sm.selectedRestaurant as selectedRestaurant) FROM PickerSession sm, Choice uc "
			+ "WHERE uc.userId = ?1 and sm.sessionId = uc.sessionId order by sm.startDate desc")
	List<PickerSessionDTO> findDTOByUserId(String userId); 	

}
