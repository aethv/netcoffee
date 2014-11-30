package com.interf.test;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.clinet.model.AccountDTO;
import com.clinet.utils.EnumUtils.Command;

public class SimpleMessenger extends UnicastRemoteObject implements Messenger {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AccountDTO accDTO;
	
	public SimpleMessenger() throws RemoteException {
		super();
	}
	
	public SimpleMessenger(AccountDTO accDTO) throws RemoteException {
		super();
		this.accDTO = accDTO;
	}

	@Override
	public AccountDTO getAccount() throws RemoteException {
		return accDTO;
	}
	
	@Override
	public void invokeCommand(Command cmd, Object... objects)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
