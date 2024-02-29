package com.gds.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import com.gds.choice.Choice;

import jakarta.persistence.*;

/**
 * Entity class for Picker Session details
 * 
 * @author Rajapriya
 *
 */

@Entity
@Table(name = "session_master")
public class PickerSession {
		
	public PickerSession() {		
	}

	public PickerSession(long sessionId, String sessionName, Date eventDate, String sessionStatus, 
			String createdBy, Date startDate) {
		super();
		this.sessionId = sessionId;
		this.sessionName = sessionName;
		this.eventDate = eventDate;
		this.sessionStatus = sessionStatus;
		this.createdBy = createdBy;
		this.startDate = startDate;		
	}

	@Id
    @Column(name="session_id", nullable=false) 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long sessionId;
	
    @Column(name="session_name", nullable=false, unique=true)
	private String sessionName;
    
    @Column(name="event_date", nullable=false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date eventDate;
    
	@Column(name="session_status", nullable=false)
	private String sessionStatus;
	
    @Column(name="created_by", nullable=false)
	private String createdBy;
	
    @Column(name="start_date", nullable=false)    
	private Date startDate;
	
    @Column(name="end_date")   
	private Date endDate;
    
	@Column(name="selected_restaurant")
	private String selectedRestaurant;
	
	@Column(name="invite_all")
	private String inviteAll;
	
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date createdTime) {
		this.startDate = createdTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endTime) {
		this.endDate = endTime;
	}

	public String getSelectedRestaurant() {
		return selectedRestaurant;
	}

	public void setSelectedRestaurant(String selectedRestaurant) {
		this.selectedRestaurant = selectedRestaurant;
	}

	public String getInviteAll() {
		return inviteAll;
	}

	public void setInviteAll(String inviteAll) {
		this.inviteAll = inviteAll;
	}

	@Override
	public String toString() {
		return "PickerSession [sessionId=" + sessionId + ", sessionName=" + sessionName + ", eventDate=" + eventDate
				+ ", sessionStatus=" + sessionStatus + ", createdBy=" + createdBy + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", selectedRestaurant=" + selectedRestaurant + ", inviteAll=" + inviteAll
				+ "]";
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "session_id")
	private List<Choice> userChoices = new ArrayList<>();

	public List<Choice> getUserChoices() {
		return userChoices;
	}

	public void setUserChoices(List<Choice> userChoices) {
		this.userChoices = userChoices;
	}
	
	/*@Column(updatable=false, insertable=false)
	//@Formula(value = "select datediff(expiry_date,issue_date) as days_remaining from document_storage")
	private Date userJoinDate;

	public Date getUserJoinDate() {
		return userJoinDate;
	}

	public void setUserJoinDate(Date userJoinDate) {
		//Checks the current login user joined this session or not
			
			this.userChoices = this.userChoices.stream()
					.filter(c -> (c.getUserId() == this.userId))
					.filter(c -> (c.getJoinDate() != null))
					.toList();
			if(choiceList.size() > 0)
				flag = true;
			
			System.out.println("isUserJoined - "+flag);
			this.userJoinedFlag = flag;
		}
		System.out.println("============ user join date ====== "+userJoinDate);
		this.userJoinDate = userJoinDate;
	}*/
	
	//@Column(updatable=false, insertable=false)
	//@JdbcTypeCode(SqlTypes.JSON)
	//@Transient	
}