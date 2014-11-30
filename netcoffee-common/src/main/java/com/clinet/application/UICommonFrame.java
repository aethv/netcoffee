package com.clinet.application;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.apache.commons.lang3.StringUtils;

import com.interf.test.Constant;

public abstract class UICommonFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String frameName;
	private int frameStatus;
	
	/**
	 * This action will be inherit by client for initializing their GUI
	 */
	protected abstract void initComponents();
	
	public abstract void updateServerStatus(int status);
	
	public UICommonFrame(String frameName){
		this.frameName = frameName;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				initFrame();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				doExitFrame();
			}
		});
	}
	
	/**
	 * if title is not set, use the constant 
	 */
	private void initFrame(){
		String title = StringUtils.isEmpty(getFrameTitle()) ? 
				Constant.APP_TITLE : StringUtils.join(Constant.APP_TITLE, Constant.STRING_SEPARATOR, getFrameTitle());
		setTitle(title);
	}
	
	private void doExitFrame(){
		if(!isFrameClosable()){
			dispose();
		}else{
			System.exit(0);
		}
	}
	
	/**
	 * Override if you want to have your own frame title
	 * @return
	 */
	protected String getFrameTitle(){
		return StringUtils.EMPTY;
	}
	
	/**
	 * need to override if you want to set it disposal only
	 */
	protected boolean isFrameClosable(){
		return true;
	}
}
