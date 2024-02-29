package com.gds.user;

import jakarta.persistence.*;

@Entity
@Table(name = "user_master")
public class User {

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
		
	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}*/

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
