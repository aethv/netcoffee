package com.clinet;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clinet.application.UILogin;
import com.clinet.application.UIMain;
import com.clinet.model.AccountDTO;
import com.clinet.utils.CommonUtils;
import com.clinet.utils.EncryptUtils;
import com.clinet.utils.ResourceUtils;
import com.interf.test.CommonRemote;
import com.interf.test.Constant;
import com.interf.test.Messenger;
import com.interf.test.SimpleMessenger;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private UILogin uiLogin = null;
	private UIMain uiMain;
	
	private CommonRemote cr;
	private Registry registry;
	
	private boolean isServerAlive = false;
	
	private String username;
	private Thread serverMonitoring;
	
	public Main() throws Exception {
		//====Start Login panel for first time
		uiLogin = new UILogin(this);
		uiLogin.setVisible(true);
		updateServerStatus(Constant.STATUS_FAILED);
		
		Thread.sleep(1500);
		
		//====Start Server Tracing
		Main.LOGGER.debug("staring Server Monitoring Thread");
		serverMonitoring = new ThreadServerMonitoring();
		serverMonitoring.start();
	}

	public static void main(String[] args) {
		// ============================================================
		try {
			LOGGER.debug("begin setting L&F");
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
			LOGGER.debug("L&F is set to: " + UIManager.getLookAndFeel().getName());
			
			/* Turn off metal's use of bold fonts */
			UIManager.put("swing.boldMetal", Boolean.FALSE);
			
			// ============================================================start application
			new Main();
		} catch (Exception e) {
			LOGGER.debug("L&F is unable to set");
		}
	}
	
	public void doExit(){
		try{
			LOGGER.debug("trying to exit application");
			if(cr != null){
				if(StringUtils.isNotEmpty(username)){
					LOGGER.debug("logout user [" + username + "]");
					cr.logout(username);
				}else{
					LOGGER.debug("User closing without logging...");
				}
			}else{
				LOGGER.debug("exiting while server not reachable");
			}
		}catch(Exception ex){
			LOGGER.error("Error when do exiting...", ex);
		}finally{
			System.exit(0);
		}
	}
	
	public boolean doLogin(String username, String password){
		try{
			if(cr == null){
				throw new Exception("Common Interface is not setup correctly");
			}
			password = EncryptUtils.getMD5Digest(password);
			AccountDTO accountDTO = new AccountDTO(username, password);
			Messenger mess = new SimpleMessenger(accountDTO);
			
			String ret = cr.login(mess);
			if(StringUtils.isNotEmpty(ret) && !ret.equals(Constant.LOGIN_FAILED)){
				return true;
			}
		}catch(Exception ex){
			LOGGER.debug("Unable to login", ex);
		}
		return false;
	}
	
	private void updateServerStatus(int status){
		if(uiLogin != null && uiLogin.isVisible()){
			uiLogin.updateServerStatus(status);
		}else if(uiMain != null && uiMain.isVisible()){
			uiMain.updateServerStatus(status);
		}
	}
	
	/**
	 * Creating connection to server for client
	 * 
	 * @author aethv
	 */
	private class ThreadServerMonitoring extends Thread {
		
		int try_times = 0;
		
		public void run() {
			while(true){
				try {
					LOGGER.debug("connecting to server");
					updateServerStatus(Constant.STATUS_PROCESSING);					
					
					registry = LocateRegistry.getRegistry(ResourceUtils.getResourceName("netFood.server.address"), Constant.RMI_PORT);
					cr = (CommonRemote) registry.lookup(Constant.RMI_ID);
					
					if(cr != null && cr.pingServer(CommonUtils.getLocalIPAddress()) > 0) {
						LOGGER.debug("Server ping successfully");
						isServerAlive = true;
						updateServerStatus(Constant.STATUS_READY);
						try_times = 0;
						SwingUtilities.invokeLater(() -> {
							if(!uiLogin.getBtnLogin().isEnabled())
								uiLogin.getBtnLogin().setEnabled(true);
						});
						sleep(10000);
					}
				} catch (Exception e) {
					LOGGER.debug("Unable to connect to server", e);
					isServerAlive = false;
					updateServerStatus(Constant.STATUS_FAILED);
					try_times++;
				}
				if(!isServerAlive && try_times == Constant.MAX_TRY){
					try{
						LOGGER.debug("Attempt reconnect " + Constant.MAX_TRY + " times but failed. Waiting " + Constant.MAX_TRY + "s for next try");
						JOptionPane.showMessageDialog(null, "Server is not ready!!! Please restart app and try again", Constant.APP_TITLE, JOptionPane.ERROR_MESSAGE);
						doExit();
					}catch(Exception e){}
				}
				try {
					if(try_times < Constant.MAX_TRY)
						LOGGER.debug("sleep");
						sleep(3000);
				} catch (Exception e1) {}
			}
		}
	}
}