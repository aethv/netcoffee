package com.interf.test;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Messenger extends UnicastRemoteObject implements MessgerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	
	protected Messenger(String username) throws RemoteException {
		super();
		this.username = username;
	}

	@Override
	public String getusername() throws RemoteException {
		return username;
	}

	@Override
	public void tell(String s) throws RemoteException {
		System.out.printf("\nMessage [%s]: %s", username, s);
	}

}
