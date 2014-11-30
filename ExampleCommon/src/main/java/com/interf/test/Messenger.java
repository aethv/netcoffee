package com.interf.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.clinet.model.AccountDTO;
import com.clinet.utils.EnumUtils.Command;

public interface Messenger extends Remote {

	public AccountDTO getAccount() throws RemoteException;
	
	public void invokeCommand(Command cmd, Object...objects) throws RemoteException;
}