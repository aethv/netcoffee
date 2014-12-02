package com.clinet;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clinet.application.UIChat;
import com.clinet.application.UIMain;
import com.clinet.external.ServerProcess;
import com.clinet.utils.CommonUtils;
import com.interf.test.CommonRemote;
import com.interf.test.Constant;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private UIChat uiChat;
	private UIMain mainApp;
	private CommonRemote cr;
	
	public Main() {
		//create server connection before GUI initializing
		//TODO create progress bar for loading
		
		try{
			Thread rmi = new Thread(new RMIManager());
			rmi.start();
			
			SwingUtilities.invokeLater(new Runnable() {
				
				public void run() {
					mainApp = new UIMain();
					mainApp.setSize(800, 600);
					CommonUtils.centerForm(mainApp);
					mainApp.setVisible(true);
					
					mainApp.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							Main.LOGGER.debug("main app closing");
						}
					});
					mainApp.addWindowStateListener(new WindowAdapter() {
						@Override
						public void windowActivated(WindowEvent e) {
							// TODO Auto-generated method stub
							super.windowActivated(e);
						}
					});
				}
			});
		}catch(Exception ex){
			LOGGER.debug("Unable to start Server application", ex);
			System.exit(1);
		}
	}
	

	
	public static void main(String[] args) {
		//============================================================
		try {
			LOGGER.debug("begin setting L&F");
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		    LOGGER.debug("L&F is set to: " + UIManager.getLookAndFeel().getName());
		} catch (Exception e) {
			LOGGER.debug("L&F is unable to set");
		}
		/* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
		
		//============================================================start application
		Main.LOGGER.debug("staring main application");
		new Main();
	}

	private class RMIManager implements Runnable{

		@Override
		public void run()
		{
			try
			{
				startChatServer();
			}
			catch (Exception e)
			{
				LOGGER.error("Could not start RMI service", e);
			}
		}

		protected void startChatServer() throws Exception{
			try {
				LOGGER.debug("Creating connection for CommonRemote");
				cr = new ServerProcess();
				Registry registry = LocateRegistry.createRegistry(Constant.RMI_PORT);
				registry.bind(Constant.RMI_ID, cr);

				LOGGER.info("Connection for CommonRemote is created");

			} catch (AlreadyBoundException | RemoteException e) {
				LOGGER.debug("unable to initialize server service");
				JOptionPane.showMessageDialog(null, "Unable to initialize Server service. \nPlease contact Administrator", Constant.APP_TITLE, JOptionPane.ERROR_MESSAGE);
				throw new Exception("unable to initialize server chat service");
			}
		}
	}
}
