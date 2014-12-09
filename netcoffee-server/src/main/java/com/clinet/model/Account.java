package com.clinet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@NamedQueries({
	@NamedQuery(
			name="Account.validate", 
			query="from Account acc WHERE acc.username = :acc and acc.password = :pwd"
	)}
)
@Entity
@Table(name = "ACCOUNTS")
public class Account implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ACCOUNT_ID", unique = true, nullable = false)
	private Long accountId;
	
	@Column(name = "USERNAME", nullable = false)
	private String username;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	@Column(name="ACTIVE")
	private boolean active;
	
	@Column(name="DISPLAY_NAME")
	private String displayName;
	
	@Column(name="LAST_LOGIN")
	private Date lastLogin;
	
	public Account(){}
	
	public Account(String username, String password) {
		this(username, password, true, username);
	}
	
	public Account(String username, String password, boolean status) {
		this(username, password, status, username);
	}
	
	public Account(String username, String password, String displayName) {
		this(username, password, true, displayName);
	}

	public Account(String username, String password, boolean active,
			String displayName) {
		super();
		this.username = username;
		this.password = password;
		this.active = active;
		this.displayName = displayName;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this);
	}
}
