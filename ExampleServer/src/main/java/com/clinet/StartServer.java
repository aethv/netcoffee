package com.clinet;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;

import com.interf.test.CommonRemote;
import com.interf.test.Constant;

public class StartServer {

	private static final Logger LOG = Logger.getLogger(StartServer.class);
	
	public static void main(String[] args) {
		try{
			CommonRemote remoteObj = new ServerProcess();
			Registry registry = LocateRegistry.createRegistry(Constant.RMI_PORT);
			registry.bind(Constant.RMI_ID, remoteObj);
			
			LOG.info("Server is started!!!");
		}catch(Exception e){
			LOG.error("Server start failed", e);
		}
	}
}
