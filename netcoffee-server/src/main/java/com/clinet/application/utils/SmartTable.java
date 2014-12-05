package com.clinet.application.utils;

import java.awt.Component;
import java.util.Arrays;
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
	
	private MyTableModel model;
	private Vector<Vector<Object>> dataSource = new Vector<Vector<Object>>();
	private Vector<String> headerSource = new Vector<String>();
	private int[] colWidth;
	
	public SmartTable() {
		model = new MyTableModel();
	}
	
	public void setData(Vector<Vector<Object>> dataValue, String[] headerName, int colWidth[]) {
		try{
			headerSource.clear();
			headerSource.addAll(Arrays.asList(headerName));
			dataSource = dataValue;
		
			model.setDataVector(dataValue, headerSource);
			this.setModel(model);
			
			initColumntable();
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			MessageUtils.showError("Unable to set data: " + ex.getMessage());
		}
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
	
	private class MyTableModel extends DefaultTableModel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private boolean isEditable = false;
		
		public MyTableModel(){
			this(false);
		}
		
		public MyTableModel(boolean isEditable){
			this.isEditable = isEditable;
		}

        public void setEditable(boolean isEditable) {
        	this.isEditable = isEditable;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columIndex) {
            return isEditable;
        }
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
}
