package com.clinet;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.clinet.model.AccountDTO;
import com.interf.test.CommonRemote;
import com.interf.test.Constant;

public class TestClient {

	public static void main(String[] args) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		CommonRemote remote = (CommonRemote) registry.lookup(Constant.RMI_ID);
		System.out.println("connected to server");
		
		AccountDTO acc1 = new AccountDTO("abc", "123");
		
		System.out.println("try to login with user: abc: " + remote.isLoginValid(acc1));
		System.out.println("try to login with user: test: " + remote.isLoginValid(new AccountDTO("def", "def")));
		
	}
}
