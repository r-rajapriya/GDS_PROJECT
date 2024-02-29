package com.gds.choice;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_choice")
public class Choice {
		
	public Choice() {		
	}

	public Choice(String choiceId, String userId, long sessionId, Date joinDate) {
		super();
		this.choiceId = choiceId;
		this.userId = userId;
		this.sessionId = sessionId;
		this.joinDate = joinDate;
	}

	@Id
    @Column(name="choice_id", nullable=false) 
	private String choiceId;
	
    @Column(name="user_id", nullable=false)
	private String userId;
	
    @Column(name="session_id", nullable=false) 
	private long sessionId;
	
    @Column(name="join_date")
	private Date joinDate;
    
	@Column(name="restaurant_name")
	private String restaurantName;
	
    @Column(name="submit_date")
	private Date submitDate;

	public String getChoiceId() {
		return choiceId;
	}

	public void setChoiceId(String choiceId) {
		this.choiceId = choiceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getSessionId() {
		return sessionId;
	}

	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getRestaurantChoice() {
		return restaurantName;
	}

	public void setRestaurantChoice(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	@Override
	public String toString() {
		return "Choice [choiceId=" + choiceId + ", userId=" + userId + ", sessionId=" + sessionId + ", joinDate="
				+ joinDate + ", restaurantName=" + restaurantName + ", submitDate=" + submitDate + "]";
	}    
}