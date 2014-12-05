package com.clinet.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.border.CompoundBorder;

import com.clinet.model.Account;

public class UCChat extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnBroadcast;
	private JList<Account> lstAccount;
	private DefaultListModel<Account> modelAccount;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JPanel panel_1;
	private JLabel lblChatBoard;
	private Icon loadingIcon;

	/**
	 * Create the panel.
	 * 
	 */
	public UCChat() {
		initComponents();
		loadingIcon = new ImageIcon(UCChat.class.getResource("/icons/loading-25x25.gif"));
	}
	
	public void startChatServer() {
		
	}

	private void initComponents() {
		this.setBorder(new CompoundBorder());
		this.setLayout(new BorderLayout(0, 0));

		panel_1 = new JPanel();
		this.add(panel_1, BorderLayout.NORTH);
		
		lblChatBoard = new JLabel("Chat board");
		panel_1.add(lblChatBoard);
		
		modelAccount = new DefaultListModel<Account>();
		lstAccount = new JList<Account>(modelAccount);
		lstAccount.setCellRenderer(new AccountCellRender());
		lstAccount.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane = new JScrollPane(lstAccount);
		this.add(scrollPane, BorderLayout.CENTER);
				
		panel = new JPanel();
		this.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 1));

		btnBroadcast = new JButton("Broadcast");
		btnBroadcast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startChatServer();
			}
		});
		this.panel.add(btnBroadcast);
	}

	public List<Account> getLstAccount() {
		List<Account> ret = new ArrayList<Account>();
		for (Object obj : modelAccount.toArray()) {
			ret.add((Account) obj);
		}

		return Collections.unmodifiableList(ret);
	}

	public boolean addAccount(Account acc) {
		if (!containAccount(acc)) {
			modelAccount.addElement(acc);

			return true;
		}
		return false;
	}

	public boolean removeAccount(Account acc) {
		if (containAccount(acc)) {
			modelAccount.removeElement(acc);

			return true;
		}
		return false;
	}

	public boolean containAccount(Account acc) {
		return modelAccount.contains(acc);
	}

	/**
	 * CellRender class for Account status watching
	 * @author aethv
	 *
	 */
	private static final class AccountCellRender extends JLabel implements ListCellRenderer<Account> {
		
		private static final long serialVersionUID = 1L;
		Icon activeIcon = new ImageIcon(UIMain.class.getResource("/icons/online-24x24.png"));
		Icon offlineIcon = new ImageIcon(UIMain.class.getResource("/icons/offline-24x24.png"));
		
		public AccountCellRender() {
			setOpaque(true);
			setHorizontalAlignment(LEFT);
			setVerticalAlignment(CENTER);
		}

		public Component getListCellRendererComponent(JList<? extends Account> list, Account acc, int index, boolean isSelected, boolean cellHasFocus) {
			// update Icon
			setIcon(acc.isActive() ? activeIcon : offlineIcon);
			setText(acc.getDisplayName());

			if (isSelected) {
				setBackground(Color.GRAY);
			} else {
				setBackground(list.getBackground());
			}

			return this;
		}
	}

	private class ChatServerLoader extends SwingWorker<Boolean, Integer>{

		@Override
		protected Boolean doInBackground() throws Exception {
			// TODO Auto-generated method stub
			publish(0);
			lblChatBoard.setForeground(Color.BLACK);
			
			return true;
		}
		
		@Override
		protected void process(List<Integer> chunks) {
			switch(chunks.get(chunks.size() - 1)){
				case 0:
				case 1:	
						lblChatBoard.setIcon(loadingIcon);
					break;
				case 2:
					break;
			}
		}
		
		@Override
		protected void done() {
			super.done();
			lblChatBoard.setIcon(null);
		}
	}
}