package com.clinet.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.clinet.model.Account;

/**
 * @author ThangNT6
 *
 */
public class UIMain extends UICommonFrame {

	private static final long serialVersionUID = 1L;

	// =====Variable define
	private JPanel pnlTop;
	private JPanel pnlBottom;
	private JPanel pnlMain;
	private UIChat uiChat;

	private JLabel lblTitle;
	private JLabel lblCopyright;

	private JButton btnAccount;
	private JButton btnOrders;
	private JButton btnFood;
	private JButton btnSetting;
	private JButton btnBroadcast;

	public UIMain() {
		super("UIMain");
		//this version not have chat
		initComponents();
		
		uiChat = new UIChat();
		getContentPane().add(uiChat, BorderLayout.EAST);
		
		// ===For test
		addTestAccountList();
		
		pack();
	}
	
	@Override
	protected String getFrameTitle() {
		return "Main Server Application";
	}
	
	@Override
	protected boolean isFrameClosable() {
		return true;
	}
	
	private void addTestAccountList() {
		uiChat.addAccount(new Account("client 1", "123456", true));
		uiChat.addAccount(new Account("client 2", "123456", true));
		uiChat.addAccount(new Account("client 3", "123456", false));
		uiChat.addAccount(new Account("client 4", "123456", true));
		uiChat.addAccount(new Account("client 5", "123456", false));
		uiChat.addAccount(new Account("client 6", "123456", false));
		uiChat.addAccount(new Account("client 7", "123456", false));
		uiChat.addAccount(new Account("client 8", "123456", false));
		uiChat.addAccount(new Account("client 9", "123456", true));
	}

	@Override
	protected void initComponents() {
		setTitle("NetCoffee Food Supplier");

		getContentPane().setLayout(new BorderLayout(0, 0));

		pnlTop = new JPanel();
		getContentPane().add(pnlTop, BorderLayout.NORTH);

		lblTitle = new JLabel("NetCoffee Supplier");
		lblTitle.setFont(new Font("MS UI Gothic", Font.PLAIN, 18));
		pnlTop.add(lblTitle);

		pnlBottom = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlBottom.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		flowLayout.setAlignOnBaseline(true);
		getContentPane().add(pnlBottom, BorderLayout.SOUTH);

		lblCopyright = new JLabel("(C) Copyright 2014, Thang Nguyen");
		lblCopyright.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCopyright.setFont(new Font("Monotype Corsiva", Font.ITALIC, 14));
		pnlBottom.add(lblCopyright);

		pnlMain = new JPanel();
		pnlMain.setBorder(new TitledBorder(null, "Controller",
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.DARK_GRAY));
		getContentPane().add(pnlMain, BorderLayout.CENTER);
		GridBagLayout gbl_pnlMain = new GridBagLayout();
		gbl_pnlMain.columnWidths = new int[] { 50, 50 };
		gbl_pnlMain.rowHeights = new int[] { 50, 50 };
		gbl_pnlMain.columnWeights = new double[] { 0.0, 0.0 };
		gbl_pnlMain.rowWeights = new double[] { 0.0, 0.0 };
		pnlMain.setLayout(gbl_pnlMain);

		btnAccount = new JButton("");
		btnAccount.setIcon(new ImageIcon(UIMain.class
				.getResource("/icons/client-128x128.png")));
		GridBagConstraints gbc_btnAccount = new GridBagConstraints();
		gbc_btnAccount.insets = new Insets(10, 10, 10, 10);
		gbc_btnAccount.gridx = 0;
		gbc_btnAccount.gridy = 0;
		pnlMain.add(btnAccount, gbc_btnAccount);

		btnOrders = new JButton("");
		btnOrders.setIcon(new ImageIcon(UIMain.class
				.getResource("/icons/order-128x128.png")));
		GridBagConstraints gbc_btnOrders = new GridBagConstraints();
		gbc_btnOrders.insets = new Insets(10, 10, 10, 10);
		gbc_btnOrders.gridx = 0;
		gbc_btnOrders.gridy = 1;
		pnlMain.add(btnOrders, gbc_btnOrders);

		btnFood = new JButton("");
		btnFood.setIcon(new ImageIcon(UIMain.class
				.getResource("/icons/food-128x128.png")));
		GridBagConstraints gbc_btnFood = new GridBagConstraints();
		gbc_btnFood.insets = new Insets(10, 10, 10, 10);
		gbc_btnFood.gridx = 1;
		gbc_btnFood.gridy = 0;
		pnlMain.add(btnFood, gbc_btnFood);

		btnSetting = new JButton("");
		btnSetting.setIcon(new ImageIcon(UIMain.class
				.getResource("/icons/setting-128x128.png")));
		GridBagConstraints gbc_btnSetting = new GridBagConstraints();
		gbc_btnSetting.insets = new Insets(10, 10, 10, 10);
		gbc_btnSetting.gridx = 1;
		gbc_btnSetting.gridy = 1;
		pnlMain.add(btnSetting, gbc_btnSetting);
	}

	@Override
	public void updateServerStatus(int status)
	{
		System.out.println(status);
	}

}