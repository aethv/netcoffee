package com.clinet.application;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.clinet.application.utils.SmartTable;

public class UCAccount extends UICommonPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnlTop;
	private JPanel pnlBottom;
	private JPanel pnlMain;
	private JButton btnClose;
	private JButton btnSave;
	private SmartTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	private UIMain main;
	
	/**
	 * Create the panel.
	 */
	public UCAccount(UIMain main) {
		super();
		this.main = main;
		initComponents();
		
		//init table generic table model
		table = new SmartTable();
		table.setEditable(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//add default value testing
		initForm();
	}
	
	private void initForm(){
//		model.addRow(new Object[]{});
	}
	
	@Override
	protected void activePanel() {
		refreshTable();
		setVisible(true);
	}

	private void refreshTable() {
		// TODO Auto-generated method stub
		
	}

	protected void initComponents() {
		setLayout(new BorderLayout(0, 0));
		
		pnlTop = new JPanel();
		add(pnlTop, BorderLayout.NORTH);
		
		JLabel lblTitle = new JLabel("User Management");
		lblTitle.setFont(new Font("MS UI Gothic", Font.BOLD, 18));
		pnlTop.add(lblTitle);
		
		pnlBottom = new JPanel();
		add(pnlBottom, BorderLayout.SOUTH);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCloseActionPerformed(e);
			}
		});
		btnClose.setMnemonic('C');
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSaveActionPerformed(e);
			}
		});
		btnSave.setMnemonic('S');
		GroupLayout gl_pnlBottom = new GroupLayout(pnlBottom);
		gl_pnlBottom.setHorizontalGroup(
			gl_pnlBottom.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlBottom.createSequentialGroup()
					.addComponent(btnSave)
					.addPreferredGap(ComponentPlacement.RELATED, 334, Short.MAX_VALUE)
					.addComponent(btnClose))
		);
		gl_pnlBottom.setVerticalGroup(
			gl_pnlBottom.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlBottom.createSequentialGroup()
					.addContainerGap(22, Short.MAX_VALUE)
					.addGroup(gl_pnlBottom.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClose)
						.addComponent(btnSave)))
		);
		pnlBottom.setLayout(gl_pnlBottom);
		
		pnlMain = new JPanel();
		add(pnlMain, BorderLayout.CENTER);
		
		table = new SmartTable();		
		pnlMain.setLayout(new BorderLayout(0, 0));
		scrollPane = new JScrollPane(table);
		pnlMain.add(scrollPane);
	}

	protected void btnSaveActionPerformed(ActionEvent e) {
		
	}

	protected void btnCloseActionPerformed(ActionEvent e) {
		main.activeMain(this);
	}
}
