package com.clinet.application.utils;

import java.awt.Component;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clinet.utils.MessageUtils;

public class SmartTable extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(SmartTable.class);
	
	private DefaultTableModel model;
	private Vector<Vector<Object>> dataSource = new Vector<Vector<Object>>();
	private Vector<String> headerSource = new Vector<String>();
	private int[] colWidth;
	
	public SmartTable() {
		model = new DefaultTableModel();
	}
	
	public void setData(Vector<Vector<Object>> dataValue, String[] headerName, int colWidth[]) {
		try{
			headerSource.clear();
			headerSource.addAll(Arrays.asList(headerName));
			dataSource = dataValue;
		
			model.setDataVector(dataValue, headerSource);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			MessageUtils.showError("Unable to set data: " + ex.getMessage());
		}
	}
	
	public void initColumnTableToCheckBox(int column) {
        getColumnModel().getColumn(column).setCellEditor(new MyTableCellCheckBox());
        getColumnModel().getColumn(column).setCellRenderer(new MyTableCellRenderCheckBox());
    }

    public void initColumnTableToComboBox(int column, Vector<Object> Source) {
        getColumnModel().getColumn(column).setCellEditor(new MyTableCellComboBox());
    }
    
    private class MyTableCellCheckBox extends AbstractCellEditor implements TableCellEditor{

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
    
    private class MyTableCellRenderCheckBox extends DefaultTableCellRenderer implements TableCellRenderer{

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// TODO Auto-generated method stub
			return null;
		}
    	
    }
    
    private class MyTableCellComboBox extends AbstractCellEditor implements TableCellEditor{

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
}
