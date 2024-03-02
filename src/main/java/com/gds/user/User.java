package com.gds.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * Entity class for User details
 * 
 * @author Rajapriya
 *
 */
@Entity
@Table(name = "user_master")
public class User {
	
	public User() {
	}
	
	public User(String userId, String userName, String password) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
	}

	@Id
    @Column(name="user_id")
	private String userId;
	
    @Column(name="user_name")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName.toUpperCase();
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
		
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [");
		builder.append(userId);
		builder.append("-->");
		builder.append(userName);
		builder.append("]");
		return builder.toString();
	}	
}
