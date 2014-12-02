package com.clinet.application;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clinet.Main;
import com.clinet.utils.CommonUtils;
import com.clinet.utils.ResourceUtils;
import com.interf.test.Constant;

public class UILogin extends UICommonFrame{

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(UILogin.class);
	private Main main;
	
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private JButton btnClose;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblStatus;
	
	private ImageIcon iconReady = new ImageIcon(UILogin.class.getResource(ResourceUtils.getResourceName("netFood.uiLogin.icon.ready")));
	private ImageIcon iconProcessing = new ImageIcon(UILogin.class.getResource(ResourceUtils.getResourceName("netFood.uiLogin.icon.processing")));
	private ImageIcon iconFailed = new ImageIcon(UILogin.class.getResource(ResourceUtils.getResourceName("netFood.uiLogin.icon.failed")));
	
	private boolean serverIsReady = false;
	
	public UILogin(Main main) {
		super(UILogin.class.toString());
		this.main = main;
		initComponents();
		
		CommonUtils.centerForm(this);
	}
	
	@Override
	protected boolean isFrameClosable() {
		return false;
	}
	
	@Override
	protected String getFrameTitle() {
		return "Login dialog";
	}
	
	@Override
	public void updateServerStatus(int status) {
		switch(status){
			case Constant.STATUS_FAILED:
				getLblStatus().setIcon(iconFailed);
				getLblStatus().setText(ResourceUtils.getResourceName("netFood.uiLogin.status.failed"));
				break;
			case Constant.STATUS_PROCESSING:
				getLblStatus().setIcon(iconProcessing);
				getLblStatus().setText(ResourceUtils.getResourceName("netFood.uiLogin.status.processing"));
				break;
			case Constant.STATUS_READY:
				getLblStatus().setIcon(iconReady);
				getLblStatus().setText(ResourceUtils.getResourceName("netFood.uiLogin.status.ready"));
				break;
		}
	}
	
	protected void login() {
		try{
			LOGGER.debug("create AccountDTO for login process");
			
			if(main.doLogin(txtUsername.getText(), txtPassword.getPassword().toString())) {
				lblStatus.setText("login successful... Please wait");
			}else{
				JOptionPane.showMessageDialog(this, "Account not found", Constant.APP_TITLE, JOptionPane.ERROR_MESSAGE);
			}
		}catch(Exception ex){
			LOGGER.error("Exception when login", ex);
		}
	}

	protected void close() {
		try{
			main.doExit();
		}catch(Exception ex){
			LOGGER.error("Exception when closing UILogin", ex);
			System.exit(0);
		}
	}
	
	private class ServerSettled extends SwingWorker<Void, Integer>{
		
		@Override
		protected Void doInBackground() throws Exception {
			int try_times = 1;
//			while(try_times <= Constant.MAX_TRY) {
//				try {
//					publish(Constant.STATUS_PROCESSING);
//					
//					LOGGER.debug("connect to server at time(s): " + try_times);
//					registry = LocateRegistry.getRegistry(ResourceUtils.getResourceName("netFood.server.address"), Constant.RMI_PORT_CHAT);
//					cr = (CommonRemote) registry.lookup(Constant.RMI_ID_CHAT);
//					
//					LOGGER.debug("server connection created with CommonRemote: " + cr);
//					if(cr != null && cr.pingServer() > 0) {
//						LOGGER.debug("Server ping successfully");
//						publish(Constant.STATUS_READY);
//						serverIsReady = true;
//						break;
//					}else {
//						LOGGER.debug("Unable to connect to server");
//						publish(Constant.STATUS_FAILED);
//						throw new RemoteException("Unable to connect to server");
//					}
//				}catch(Exception ex) {
//					LOGGER.debug("error occurred in server connection establishment");
//					LOGGER.error(ex.getMessage(), ex);
//					try_times++;
//					publish(Constant.STATUS_FAILED);
//					serverIsReady = false;
//					try{
//						if(try_times != Constant.MAX_TRY){
//							Thread.sleep(2000);
//						}
//					}catch(Exception e){}
//				}
//			}
			return null;
		}
		
		@Override
		protected void process(List<Integer> chunks) {
			super.process(chunks);
			
			switch(chunks.get(0)){
				case Constant.STATUS_FAILED:
					lblStatus.setIcon(iconFailed);
					lblStatus.setText(ResourceUtils.getResourceName("netFood.uiLogin.status.failed"));
					break;
				case Constant.STATUS_PROCESSING:
					lblStatus.setIcon(iconProcessing);
					lblStatus.setText(ResourceUtils.getResourceName("netFood.uiLogin.status.processing"));
					break;
				case Constant.STATUS_READY:
					lblStatus.setIcon(iconReady);
					lblStatus.setText(ResourceUtils.getResourceName("netFood.uiLogin.status.ready"));
					break;
			}
		}
		
		@Override
		protected void done() {
			super.done();
			if(!serverIsReady){
				JOptionPane.showMessageDialog(null, "Server is not ready!!! Please close and try again", "NetFood", JOptionPane.ERROR_MESSAGE);
				btnLogin.setEnabled(false);
				close();
			}else{
				btnLogin.setEnabled(true);
			}
		}
	}

	protected void initComponents() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
//				UILogin.this.windowActivated();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
			
		});
		setType(Type.UTILITY);
		setResizable(false);
		setTitle("Login - FoodNet");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(UIManager.getColor("Panel.background"));
		getContentPane().add(panel_2, BorderLayout.SOUTH);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btnLogin.setMnemonic('L');
		btnLogin.setEnabled(false);
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		btnClose.setMnemonic('C');
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_2.add(btnLogin);
		panel_2.add(btnClose);
		
		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3, BorderLayout.CENTER);
		
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtUsername.setText("admin");
		txtUsername.setColumns(10);
		
		lblUsername = new JLabel("Username");
		lblUsername.setLabelFor(txtUsername);
		lblUsername.setDisplayedMnemonic('U');
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		lblPassword = new JLabel("Password");
		lblPassword.setDisplayedMnemonic('p');
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		txtPassword = new JPasswordField();
		lblPassword.setLabelFor(txtPassword);
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		lblStatus = new JLabel("Connecting to server");
		lblStatus.setIcon(new ImageIcon(ResourceUtils.getResourceName("netFood.uiLogin.icon.processing")));
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(lblUsername)
							.addGap(8)
							.addComponent(txtUsername))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(lblPassword)
							.addGap(11)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(lblStatus)
								.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(0, Short.MAX_VALUE))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsername))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
					.addComponent(lblStatus)
					.addContainerGap())
		);
		panel_3.setLayout(gl_panel_3);
		pack();
	}

	public JButton getBtnLogin() { return btnLogin; }
	
	public JLabel getLblStatus() {
		return lblStatus;
	}
}
