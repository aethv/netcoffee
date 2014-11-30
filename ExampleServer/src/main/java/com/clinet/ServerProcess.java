package com.clinet;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.clinet.dao.AccountDAO;
import com.clinet.model.Account;
import com.clinet.model.AccountDTO;
import com.clinet.model.Product;
import com.interf.test.CommonRemote;
import com.interf.test.MessgerInterface;

public class ServerProcess extends UnicastRemoteObject implements CommonRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ServerProcess.class);
	private AccountDAO accDAO = new AccountDAO();
	
	public ServerProcess() throws RemoteException {
		super();
	}

	public boolean isLoginValid(AccountDTO accDTO) throws RemoteException {
		Account acc = accDAO.validateLogin(accDTO);
		if(acc == null){
			return false;
		}
		
		if(acc.getStatus() == 0){
			return false;
		}
		
		acc.setLastLogined(Calendar.getInstance().getTimeInMillis());
		accDAO.update(acc);
		return true;
	}

	public void logout(String username) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public List<Product> getListProduct() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public String broadcastMessage(String s, MessgerInterface from)
			throws RemoteException {
        System.out.println("Server said : '" + s + "' you bitch !!! ");
        return null;
	}

}
