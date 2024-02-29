package com.gds.session;

import java.util.Date;

public class PickerSessionDTO {	
	
	public PickerSessionDTO() {		
	}

	public PickerSessionDTO(long sessionId, String sessionName, String sessionStatus, Date eventDate, String createdBy,
			Date userJoinDate, String selectedRestaurant) {
		super();
		this.sessionId = sessionId;
		this.sessionName = sessionName;
		this.sessionStatus = sessionStatus;
		this.eventDate = eventDate;
		this.createdBy = createdBy;
		this.userJoinDate = userJoinDate;
		this.selectedRestaurant = selectedRestaurant;
	}
	
	private long sessionId;
	private String sessionName;		
	private String sessionStatus;
	private Date eventDate;
	private String createdBy;	
	private Date userJoinDate;
	private String selectedRestaurant;	
	
	public long getSessionId() {
		return sessionId;
	}
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public String getSessionStatus() {
		return sessionStatus;
	}
	public void setSessionStatus(String sessionStatus) {
		this.sessionStatus = sessionStatus;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getSelectedRestaurant() {
		return selectedRestaurant;
	}
	public void setSelectedRestaurant(String selectedRestaurant) {
		this.selectedRestaurant = selectedRestaurant;
	}
	public Date getUserJoinDate() {
		return userJoinDate;
	}
	public void setUserJoinDate(Date userJoinDate) {
		this.userJoinDate = userJoinDate;
	}
}