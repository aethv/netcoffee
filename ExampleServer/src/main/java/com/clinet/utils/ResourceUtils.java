package com.clinet.utils;

import java.util.ResourceBundle;

public class ResourceUtils {
	
	private static ResourceBundle rb = null;
	private static final String APPLICATION_PROPERTIES = "application";
	
	public static void initResources(){
        rb = ResourceBundle.getBundle(APPLICATION_PROPERTIES);
    }
	
	public static String getResourceName(String key){
        try{
            if(rb == null) initResources();
            return rb.getString(key);
        }
        catch(Exception ex){
        	ex.printStackTrace();
            return null;
        }
    }
	
	public static void main(String[] args) {
		System.out.println(getResourceName("hibernate.dialect"));
		System.out.println(getResourceName("thang"));
	}
}
