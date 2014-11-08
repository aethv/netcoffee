package com.interf.test;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.clinet.model.Product;

public interface TestRemote extends Remote {

	public boolean isLoginValid(String username, String password) throws RemoteException;
	
	public void logout(String username) throws RemoteException;
	
	public List<Product> getMenu() throws RemoteException;
}
