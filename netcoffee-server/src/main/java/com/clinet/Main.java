package com.clinet;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clinet.application.UIMain;
import com.clinet.external.ServerProcess;
import com.clinet.utils.CommonUtils;
import com.clinet.utils.MessageUtils;
import com.interf.test.CommonRemote;
import com.interf.test.Constant;

public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private UIMain mainApp;
	private CommonRemote cr;
	
	//TODO change to common resource
	private static final String STR_PROGRESS_LOADING = "Loading...";
	
	public Main() {
		try{
			LOGGER.debug("==============BEGIN");
			RMIManager rmi = new RMIManager();
			rmi.start();
			rmi.showDialog();
			
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
				}
			});
		}catch(Exception ex){
			LOGGER.error("Unable to start Main application", ex);
			System.exit(1);
		}finally{
			
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
		    
		    /* Turn off metal's use of bold fonts */
	        UIManager.put("swing.boldMetal", Boolean.FALSE);
			
			//============================================================start application
			Main.LOGGER.debug("staring main application");
			new Main();
			
		} catch (ExceptionInInitializerError | Exception e) {
			LOGGER.debug("***********************L&F is unable to set");
			MessageUtils.showError("AAAAAAAAAAAAA");
		}
	}

	private class RMIManager extends Thread implements Runnable{
		private JDialog dalg;
		private JProgressBar progress;
		
		@Override
		public void run()
		{
			try
			{
				LOGGER.debug("started");
				startChatServer();
			}
			catch (Exception e)
			{
				LOGGER.error("Could not start RMI service", e);
				MessageUtils.showError("Could not start RMI service, cause: " + e.getMessage());
			}
			finally
			{
				closeDialog();
				LOGGER.debug("================finished");
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
				throw new Exception("unable to initialize server chat service");
			}
		}
		
		public void showDialog(){
			try{
				dalg = new JDialog(mainApp, true);
				progress = new JProgressBar();
				progress.setIndeterminate(true);
				progress.setStringPainted(true);
				progress.setString(STR_PROGRESS_LOADING);
				dalg.setTitle(Constant.APP_TITLE);
				dalg.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	            dalg.setSize(250, 80);
	            dalg.getContentPane().setLayout(new BorderLayout());
	            dalg.getContentPane().add(progress);
	            CommonUtils.centerForm(dalg);
	            dalg.setVisible(true);
			}catch(Exception ex){
				LOGGER.debug("unable to show dialog", ex);
				MessageUtils.showError("Unable to show dialog " + ex.getMessage());
			}
		}
		
		public void closeDialog(){
			if(dalg != null){
				dalg.dispose();
				dalg.setVisible(false);
				dalg = null;
			}
		}
	}
}
