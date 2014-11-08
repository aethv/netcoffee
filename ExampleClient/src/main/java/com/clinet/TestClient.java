package com.clinet;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.interf.test.Constant;
import com.interf.test.TestRemote;

public class TestClient {

	public static void main(String[] args) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		TestRemote remote = (TestRemote) registry.lookup(Constant.RMI_ID);
		System.out.println("connected to server");
		
		System.out.println("try to login with user: abc: " + remote.isLoginValid("abc", "123"));
		System.out.println("try to login with user: test: " + remote.isLoginValid("test", "123"));
		
	}
}
