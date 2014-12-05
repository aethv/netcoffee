package com.clinet.application;

import javax.swing.JPanel;

public abstract class UICommonPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected abstract void initComponents();
	
	protected void activePanel(){
		
	}
	
	protected void releasePanel(){
		
	}
}
