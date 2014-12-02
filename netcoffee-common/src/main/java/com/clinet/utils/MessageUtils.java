package com.clinet.utils;

import javax.swing.JOptionPane;

import com.interf.test.Constant;

public class MessageUtils {

	private static final String TITLE = Constant.APP_TITLE;
	
	public static void showInfo(String mess){
		JOptionPane.showMessageDialog(null, mess, TITLE, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showError(String mess){
		JOptionPane.showMessageDialog(null,  mess, TITLE, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void showWarn(String mess){
		JOptionPane.showMessageDialog(null,  mess, TITLE, JOptionPane.WARNING_MESSAGE);
	}
}
