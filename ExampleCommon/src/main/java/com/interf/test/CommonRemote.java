package com.interf.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CommonRemote extends Remote {

	public int pingServer(String ip) throws RemoteException;
	
	public String login(Messenger messInterface) throws RemoteException;
	
	public void logout(String username) throws RemoteException;
	
	public void sendMessage(Messenger messgerInterface, String msg) throws RemoteException;
}