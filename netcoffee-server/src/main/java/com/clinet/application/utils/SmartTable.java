package com.clinet.application.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clinet.utils.MessageUtils;

public class SmartTable extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(SmartTable.class);
	
	private static final Color ROW_COLOR_ODD = Color.LIGHT_GRAY;
	private static final Color ROW_COLOR_EVEN = new java.awt.Color(255, 255, 255);
	private static final Color ROW_COLOR_SELECTED = new java.awt.Color(255, 255, 153);
	
	private MyTableModel model = new MyTableModel();
	private MyTableCellRender myCellRender = new MyTableCellRender();
	
	private Vector<Vector<Object>> dataSource = new Vector<Vector<Object>>();
	private Vector<String> headerSource = new Vector<String>();
	private int[] updatableTableCell;
	
	public SmartTable() {
		initialize();
		this.setDefaultRenderer(Object.class, myCellRender);
		
		repaint();
	}
	
	private void initialize() {
		//Mass data of table
		String[] headers = {"Title 1", "Title 2", "Title 3", "Title 4"};
		Vector<Object> vRow1 = new Vector<Object>(Arrays.asList(new Object[]{"Row 1-1", false, true, "Row 5-4-3"}));;
		Vector<Object> vRow2 = new Vector<Object>(Arrays.asList(new Object[]{"Row 1-1", true, false, "Row 5-4-3"}));;
		Vector<Object> vRow3 = new Vector<Object>(Arrays.asList(new Object[]{"Row 1-1", true, true, "Row 5-4-3"}));;
		Vector<Object> vRow4 = new Vector<Object>(Arrays.asList(new Object[]{"Row 1-1", false, false, "Row 5-4-3"}));;
		Vector<Object> vRow5 = new Vector<Object>(Arrays.asList(new Object[]{"Row 1-1", true, false, "Row 5-4-3"}));;
		
		Vector<Vector<Object>> vRows = new Vector<Vector<Object>>(Arrays.asList(vRow1, vRow2, vRow3, vRow4, vRow5));
		setData(vRows, headers, new int[]{50, 50, 50, 50});
		
		setSelectionForeground(new java.awt.Color(0, 0, 0));

		//Add event
		addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
		});
	}
	
	public void setUpdateTableCell(int[] updatableTableCell){
		this.updatableTableCell = updatableTableCell;
	}

	protected void TableMouseClicked(MouseEvent evt) {
		if (evt.getClickCount() == 2) {
            fireMouseDoubleClickEvent(new MyEvent(this));
        } else {
            fireMouseClickEvent(new MyEvent(this));
        }
	}

	public void setData(Vector<Vector<Object>> dataValue, String[] headerName, int colWidth[]) {
		try{
			headerSource.clear();
			headerSource.addAll(Arrays.asList(headerName));
			dataSource = dataValue;
		
			model.setDataVector(dataValue, headerSource);
			this.setModel(model);
			
			//===============================
			initColumntable();
			for(int i = 0; i < model.getColumnCount(); i++){
				getColumnModel().getColumn(i).setHeaderValue(headerSource.get(i));
				getColumnModel().getColumn(i).setPreferredWidth(colWidth[i]);
			}
			
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			MessageUtils.showError("Unable to set data: " + ex.getMessage());
		}
	}
	
	public void deleteRow(int rowIdx){
		dataSource.remove(rowIdx);
		model.fireTableRowsDeleted(rowIdx, rowIdx);
	}
	
	public void deleteRow(Vector<Object> items){
		int rowIdx = dataSource.indexOf(items);
		dataSource.remove(items);
		model.fireTableRowsDeleted(rowIdx, rowIdx);
	}
	
	public void updateRow(Vector<Object> item, int rowIdx){
		dataSource.set(rowIdx, item);
		model.fireTableRowsUpdated(rowIdx, rowIdx);
	}
	
	private void initColumntable() {
		if (CollectionUtils.isEmpty(dataSource)) {
            return;
        }
        Vector<Object> value = dataSource.elementAt(0);
        for (int i = 0; i < value.size(); ++i) {
        	if (value.elementAt(i) instanceof Boolean) {
                initColumnTableToCheckBox(i);
        	}else if(value.elementAt(i) instanceof String[]){
        		initColumnTableToComboBox(i, Arrays.asList(value.elementAt(i)));
        	}
        }
	}
	
	private void initColumnTableToCheckBox(int column) {
        getColumnModel().getColumn(column).setCellRenderer(new MyTableCellCheckBoxRender());
    }

	private void initColumnTableToComboBox(int column, List<Object> source) {
        getColumnModel().getColumn(column).setCellEditor(new MyTableCellComboBoxEditor(source));
    }
	
	
	public void setEditable(boolean b){
		model.setEditable(b);
	}
	
	private class MyTableModel extends DefaultTableModel{
		
		private static final long serialVersionUID = 1L;
		private boolean isEditable = false;
		
        public void setEditable(boolean isEditable) {
        	this.isEditable = isEditable;
        }
        
        @Override
        public boolean isCellEditable(int rowIndex, int columIndex) {
        	if(updatableTableCell == null || updatableTableCell.length == 0){
        		return isEditable;
        	}
        	for(int i = 0; i < updatableTableCell.length; i++){
        		if(columIndex == updatableTableCell[i]){
        			return true;
        		}
        	}
            return false;
        }
	}
    
	private class MyTableCellRender extends DefaultTableCellRenderer{

		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if(row % 2 == 0){
				comp.setBackground(ROW_COLOR_EVEN);
			}else{
				comp.setBackground(ROW_COLOR_ODD);
			}
			if(isSelected){
				comp.setBackground(ROW_COLOR_SELECTED);
			}
			setBorder(null);
			
			return comp;
		}
	}
	
	public void newRow(Vector<Object> row, boolean isSelectNewRow) {
		dataSource.add(row);
		model.fireTableRowsInserted(model.getRowCount() -1, model.getRowCount() -1);
		setRowSelectionInterval(model.getRowCount() -1, model.getRowCount() -1);
		scrollRectToVisible(getCellRect(getSelectedRow(), 0, true));
	}
	
	public void updateRow(Vector<Object> row, int idx, boolean isSelectNewRow){
		dataSource.set(idx, row);
		model.fireTableRowsUpdated(idx, idx);
	}
	
	@SuppressWarnings("unused")
	private class MyTableCellCheckBoxEditor extends AbstractCellEditor implements TableCellEditor{

		private static final long serialVersionUID = 1L;
		public JCheckBox chk = getCheckBox();
		
		private JCheckBox getCheckBox(){
			JCheckBox tmp = new JCheckBox();
			tmp.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(isEditing()){
						getCellEditor().stopCellEditing();
					}
				}
			});
			return tmp;
		}

		@Override
		public Object getCellEditorValue() {
			return chk.isSelected();
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			chk.setSelected((Boolean) value);
			chk.setHorizontalAlignment(SwingConstants.LEFT);
			return chk;
		}
    }
    
	private class MyTableCellCheckBoxRender extends DefaultTableCellRenderer implements TableCellRenderer{

		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			
			JCheckBox chk = new JCheckBox();
			chk.setSelected((Boolean) value);
			chk.setHorizontalAlignment(SwingConstants.CENTER);
			chk.setOpaque(true);

			if(row % 2 == 0){
				chk.setBackground(ROW_COLOR_EVEN);
			}else{
				chk.setBackground(ROW_COLOR_ODD);
			}
			if(isSelected){
				chk.setBackground(ROW_COLOR_SELECTED);
			}
			
			
			return chk;
		}
    }
    
    
	/**
     * ComboBox editor for JTable
     * @author aethv
     *
     */
    private class MyTableCellComboBoxEditor extends AbstractCellEditor implements TableCellEditor{

		private static final long serialVersionUID = 1L;
		private Object source;
		private List<Object> lstObject;
    	
    	public MyTableCellComboBoxEditor(List<Object> lstObject) {
            this.lstObject = lstObject;
        }
    	
		@Override
		public Object getCellEditorValue() {
			return source;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			JComboBox<Object> cbo = new JComboBox<Object>(lstObject.toArray());
            cbo.addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if (isEditing()) {
                        getCellEditor().stopCellEditing();
                        source = value;
                    }
                }
            });
            cbo.setSelectedItem(value);
			
			return cbo;
		}
    }
    
    
    //=====================================================
    //=======================================EVENT LISTENER
	// Create the listener list
	protected javax.swing.event.EventListenerList lstEventListener = new javax.swing.event.EventListenerList();

	public void addSmartTableEventListener(SmartTableEventListener listener) {
		lstEventListener.add(SmartTableEventListener.class, listener);
	}

	public void removeSmartTableEventListener(SmartTableEventListener listener) {
		lstEventListener.remove(SmartTableEventListener.class, listener);
	}

	void fireMouseDoubleClickEvent(MyEvent evt) {
		Object[] listeners = lstEventListener.getListenerList();
		for (int i = 0; i < listeners.length; i++) {
			if (listeners[i] == SmartTableEventListener.class) {
				((SmartTableEventListener) listeners[i]).mouseDoubleClick(evt);
			}
		}
	}
	
    void fireMouseClickEvent(MyEvent evt) {
        Object[] listeners = lstEventListener.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == SmartTableEventListener.class) {
                ((SmartTableEventListener) listeners[i]).mouseClick(evt);
            }
        }
    }

	// Declare the event. It must extend EventObject.
	public class MyEvent extends EventObject {

		private static final long serialVersionUID = 1L;

		public MyEvent(Object source) {
			super(source);
		}
	}

	
	public interface SmartTableEventListener extends EventListener {

		public void mouseDoubleClick(MyEvent evt);

		public void mouseClick(MyEvent evt);
	}
}