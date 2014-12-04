package com.clinet.application;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

public class UIUserManagement extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnlTop;
	private JPanel pnlBottom;
	private JPanel pnlMain;
	private JButton btnClose;
	private JButton btnSave;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	
	/**
	 * Create the panel.
	 */
	public UIUserManagement() {
		initialize();
		
		//init table generic table model
		model = new DefaultTableModel();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		
		pnlTop = new JPanel();
		add(pnlTop, BorderLayout.NORTH);
		
		JLabel lblTitle = new JLabel("User Management");
		lblTitle.setFont(new Font("MS UI Gothic", Font.BOLD, 18));
		pnlTop.add(lblTitle);
		
		pnlBottom = new JPanel();
		add(pnlBottom, BorderLayout.SOUTH);
		
		btnClose = new JButton("Close");
		btnClose.setMnemonic('C');
		
		btnSave = new JButton("Save");
		btnSave.setMnemonic('S');
		GroupLayout gl_pnlBottom = new GroupLayout(pnlBottom);
		gl_pnlBottom.setHorizontalGroup(
			gl_pnlBottom.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlBottom.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnSave)
					.addPreferredGap(ComponentPlacement.RELATED, 314, Short.MAX_VALUE)
					.addComponent(btnClose)
					.addContainerGap())
		);
		gl_pnlBottom.setVerticalGroup(
			gl_pnlBottom.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_pnlBottom.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlBottom.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnClose)
						.addComponent(btnSave))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlBottom.setLayout(gl_pnlBottom);
		
		pnlMain = new JPanel();
		add(pnlMain, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"", "Username", "Display Name", "Status", "Last logined"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, true, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(1).setPreferredWidth(85);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setMinWidth(75);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		
		scrollPane = new JScrollPane(table);
		pnlMain.add(scrollPane);
	}
}
