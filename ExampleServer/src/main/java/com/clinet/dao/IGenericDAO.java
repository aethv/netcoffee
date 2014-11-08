package com.clinet.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T extends Serializable> {
    
    /**
     * 
     * This method is responsable for verify if the object exist in the DB.
     * 
     * @param t
     * @return true if it's correct         
     */
    boolean verification(T t);
    
    /**
     * 
     * This method, when implemented, is reponsable to add all the objects
     * on BD
     * 
     * @param t
     * @return true if it's correct
     */
    boolean addObject(T t) throws Exception;
    
    /**
     * 
     * This method, when implemented, is reponsable to remove all the objects
     * on BD
     * 
     * @param t
     * @return true if ti's coorect
     */
    boolean removeObject(T t) throws Exception;
    
    /**
     * 
     * This method, when implemented, is reponsable to update all the objects
     * on BD
     * 
     * @param t
     * @return true if ti's coorect
     */
    boolean updateObject(T t) throws Exception;    
    
    List<T> searchForAttributeString(String attribute, String search) throws Exception;
    T searchObjectForNameLastName(String name, String lastName) throws Exception;
    T searchObjectForName(String name) throws Exception;
    T searchObjectForID(Long id) throws Exception;
    
}