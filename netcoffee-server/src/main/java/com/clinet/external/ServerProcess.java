package com.clinet.external;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clinet.dao.AccountDAO;
import com.clinet.model.Account;
import com.clinet.utils.CommonUtils;
import com.interf.test.CommonRemote;
import com.interf.test.Constant;
import com.interf.test.Messenger;

public class ServerProcess extends UnicastRemoteObject implements CommonRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(ServerProcess.class);
	private AccountDAO accDAO = new AccountDAO();
	private Map<String, Messenger> mapUser = new HashMap<String, Messenger>();
	
	public ServerProcess() throws RemoteException {
		super();
	}

	public String login(Messenger messInterface) throws RemoteException {
		LOGGER.debug("loging to system by user: " + messInterface.getAccount());
		String token = StringUtils.EMPTY;
		try {
			Account acc = accDAO.validateLogin(messInterface.getAccount());
			LOGGER.debug("get acc from db: " + acc);
			
			if(acc == null){
				return StringUtils.EMPTY;
			}
			if(!acc.isActive()){
				return Constant.LOGIN_FAILED;
			}
			
			LOGGER.info("logined successfully by user: " + acc.getUsername());
			token = CommonUtils.getRandom8Chars();
			acc.setLastLogin(Calendar.getInstance().getTime());
			acc.setToken(token);
			
			accDAO.update(acc);
			mapUser.put(acc.getUsername(), messInterface);
		}catch(Exception ex) {
			LOGGER.error("Unable to login", ex);
			token = Constant.LOGIN_FAILED;
		}finally {
			LOGGER.debug("done processing login");
		}
		return token;
	}

	public void logout(String username) throws RemoteException {
		LOGGER.debug("trying to logout user: " + username);
		if(! mapUser.containsKey(username)) {
			LOGGER.error("Big problem here!!!!. Logout by unfound user: " + username);
		} else {
			mapUser.remove(username);
			LOGGER.info("logout successfully by user: " + username);
		}
	} 

	public void sendMessage(Messenger messgerInterface, String msg)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public int pingServer(String ip) throws RemoteException {
		LOGGER.info("Got connection from " + ip);
		return 1;
	}
	
	
}
