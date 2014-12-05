package com.clinet.application.utils;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
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
	
	private MyTableModel model = new MyTableModel();
	private Vector<Vector<Object>> dataSource = new Vector<Vector<Object>>();
	private Vector<String> headerSource = new Vector<String>();
	
	public SmartTable() {
		initialize();
	}
	
	private void initialize() {
		//Mass data of table
		String[] headers = {"Title 1", "Title 2", "Title 3", "Title 4"};
		Vector<Object> vRow1 = new Vector<Object>(Arrays.asList(new String[]{"Row 1-1", "Row 1-2", "Row 1-3", "Row 1-4"}));;
		Vector<Object> vRow2 = new Vector<Object>(Arrays.asList(new String[]{"Row 2-1", "Row 2-2", "Row 2-3", "Row 2-4"}));;
		Vector<Object> vRow3 = new Vector<Object>(Arrays.asList(new String[]{"Row 3-1", "Row 3-2", "Row 3-3", "Row 3-4"}));;
		Vector<Object> vRow4 = new Vector<Object>(Arrays.asList(new String[]{"Row 4-1", "Row 4-2", "Row 4-3", "Row 4-4"}));;
		Vector<Vector<Object>> vRows = new Vector<Vector<Object>>(Arrays.asList(vRow1, vRow2, vRow3, vRow4));
		setData(vRows, headers, new int[]{50, 50, 50, 50});
		
		setSelectionBackground(new java.awt.Color(255, 255, 153));
		setSelectionForeground(new java.awt.Color(0, 0, 0));
		
		//Add event
		addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableMouseClicked(evt);
            }
		});
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
        getColumnModel().getColumn(column).setCellEditor(new MyTableCellCheckBoxEditor());
        getColumnModel().getColumn(column).setCellRenderer(new MyTableCellCheckBoxRender());
    }

	private void initColumnTableToComboBox(int column, List<Object> source) {
        getColumnModel().getColumn(column).setCellEditor(new MyTableCellComboBoxEditor(source));
    }
	
	
	public void setEditable(boolean b){
		model.setEditable(b);
	}
	
	private class MyTableModel extends DefaultTableModel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private boolean isEditable = false;
		
        public void setEditable(boolean isEditable) {
        	this.isEditable = isEditable;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columIndex) {
            return isEditable;
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
	
	private class MyTableCellCheckBoxEditor extends AbstractCellEditor implements TableCellEditor{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			// TODO Auto-generated method stub
			return null;
		}
    }
    
    
	private class MyTableCellCheckBoxRender extends DefaultTableCellRenderer implements TableCellRenderer{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// TODO Auto-generated method stub
			return null;
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