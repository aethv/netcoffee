package com.clinet.application;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.clinet.Main;

public class UIMain extends UICommonFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Main main;
	
	public UIMain(Main main){
		super("UIMain");
		this.main = main;
	}
	
	@Override
	protected String getFrameTitle() {
		return "Main Client application";
	}
	
	@Override
	protected boolean isFrameClosable() {
		return false;
	}

	@Override
	protected void initComponents() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	@Override
	public void updateServerStatus(int status) {
		// TODO Auto-generated method stub
		
	}

}
