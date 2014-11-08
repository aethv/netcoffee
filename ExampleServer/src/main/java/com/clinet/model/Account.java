package com.clinet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "ACCOUNTS")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ACCOUNT_ID", unique = true, nullable = false)
	private Long accountId;
	
	@Column(name="STATUS")
	private int status;
	
	@Column(name="LAST_LOGINED")
	private int lastLogined;
	
	@Column(name="DISPLAY_NAME")
	private String displayName;
	
	public Account(){}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getLastLogined() {
		return lastLogined;
	}

	public void setLastLogined(int lastLogined) {
		this.lastLogined = lastLogined;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
