package com.interf.test;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.clinet.model.AccountDTO;
import com.clinet.model.Product;

public interface CommonRemote extends Remote {

	public boolean isLoginValid(AccountDTO accDTO) throws RemoteException;
	
	public void logout(String username) throws RemoteException;
	
	public List<Product> getListProduct() throws RemoteException;
	
	public String broadcastMessage(String s, MessgerInterface from) throws RemoteException;
	
}