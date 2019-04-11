package com.task.project.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Create Table or Watch
@Entity
@Table(name="Users", schema = "public")
public class User {
	
	@Id
	@Column(name="logonID")
	private String logonID;
	
	@Column(name="name")
	private String name;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email")
	private String email;
	
//	Create Structure User
	public User() {}
	
	public User(String logonID, String name, String password, String email)
	{
		this.logonID = logonID;
		this.name = name;
		this.password = password;
		this.email = email;
	}
	
	public String getLogonID() {
		return logonID;
	}
	
	public void setLogonID(String logonID) {
		this.logonID = logonID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
