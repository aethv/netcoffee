package com.clinet.application;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clinet.application.utils.SmartTable;
import com.clinet.dao.AccountDAO;
import com.clinet.model.Account;
import com.clinet.utils.MessageUtils;

public class UCAccount extends UICommonPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(UCAccount.class);
	
	private JPanel pnlTop;
	private JPanel pnlBottom;
	private JPanel pnlMain;
	private JButton btnClose;
	private JButton btnSave;
	private SmartTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	private UIMain main;
	
	private List<Account> lstAccount = new ArrayList<Account>();
	private AccountDAO accountDAO;
	
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
		accountDAO = new AccountDAO();
		
		refreshTable();
	}
	
	@Override
	protected void activePanel() {
		refreshTable();
		setVisible(true);
	}

	private void refreshTable() {
		try{
			lstAccount = accountDAO.findAll();
			Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();
			
			Vector<Object> vRow = null;
			for(Account acc : lstAccount){
				vRow = new Vector<Object>();
				vRow.add(acc);
				vRow.add(acc.getAccountId());
				vRow.add(acc.getUsername());
				vRow.add(acc.getDisplayName());
				vRow.add(acc.isActive());
				if(acc.getLastLogin() == null){
					vRow.add(StringUtils.EMPTY);
				}else{
					vRow.add(acc.getLastLogin());
				}
				tableData.add(vRow);
			}
			table.setData(tableData, new String[]{"ID", "Username", "Display name", "Status", "Last login"}, new int[]{50, 100, 100, 50, 100});
		}catch(Exception ex){
			LOGGER.debug("Unable to refresh table data", ex);
			MessageUtils.showError("Unable to refresh table data: " + ex.getMessage());
		}
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
