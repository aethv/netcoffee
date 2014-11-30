package com.interf.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessgerInterface extends Remote {

	public String getusername() throws RemoteException;
	
	public void tell(String s) throws RemoteException;
}