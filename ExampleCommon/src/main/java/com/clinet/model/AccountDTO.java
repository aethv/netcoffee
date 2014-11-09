package com.clinet.model;

import java.io.Serializable;

public class AccountDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private int loginedTime;
	private boolean isActived;
	private String message;

	public AccountDTO(){}
	
	public AccountDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.isActived = true;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLoginedTime() {
		return loginedTime;
	}

	public void setLoginedTime(int loginedTime) {
		this.loginedTime = loginedTime;
	}

	public boolean isActived() {
		return isActived;
	}

	public void setActived(boolean isActived) {
		this.isActived = isActived;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
