package com.clinet.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

import com.clinet.exception.DataAccessLayerException;
import com.clinet.utils.HibernateFactory;

public abstract class AbstractDao<T extends Serializable> implements IAbstractDAO<T> {
	
    protected Session session;
    protected Transaction tx;
    private Class<T> type;
    
    private static final Logger log = Logger.getLogger(AbstractDao.class);

    public AbstractDao(Class<T> type) {
    	this.type = type;
        HibernateFactory.buildIfNeeded();
    }

    protected void handleException(HibernateException e) throws DataAccessLayerException {
        HibernateFactory.rollback(tx);
        throw new DataAccessLayerException(e);
    }

    protected void startOperation() throws HibernateException {
    	log.info("Enter startOperation: " + session);
    	
        session = HibernateFactory.openSession();
        tx = session.beginTransaction();
        
        log.info("End startOperation: " + session + " - " + tx);
    }
    
    public void insert(T t) {
		try{
			startOperation();
			session.save(t);
			tx.commit();
		}catch(HibernateException e){
			handleException(e);
		}finally{
			HibernateFactory.close(session);
		}
	}

	public void insert(List<T> lst) {
		try{
			startOperation();
			for(T obj : lst){
				session.save(obj);
			}
			tx.commit();
		}catch(HibernateException e){
			handleException(e);
		}finally{
			HibernateFactory.close(session);
		}
	}

	public void delete(T t) {
		try{
			startOperation();
			session.delete(t);
			tx.commit();
		}catch(HibernateException e){
			handleException(e);
		}finally{
			HibernateFactory.close(session);
		}
	}

	public void delete(List<T> lst) {
		try{
			startOperation();
			for(T obj : lst){
				session.delete(obj);
			}
			tx.commit();
		}catch(HibernateException e){
			handleException(e);
		}finally{
			HibernateFactory.close(session);
		}
	}

	public void update(T t) {
		try{
			startOperation();
			session.update(t);
			tx.commit();
		}catch(HibernateException e){
			handleException(e);
		}finally{
			HibernateFactory.close(session);
		}
	}

	public void update(List<T> lst) {
		try{
			startOperation();
			for(T obj : lst){
				session.update(obj);
			}
			tx.commit();
		}catch(HibernateException e){
			handleException(e);
		}finally{
			HibernateFactory.close(session);
		}
	}

	@SuppressWarnings("unchecked")
	public T findOne(Long id) {
		T t = null;
		try{
			startOperation();
			t = (T) session.get(type, id);
			tx.commit();
		}catch(HibernateException e){
			handleException(e);
		}finally{
			HibernateFactory.close(session);
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		List<T> objects = null;
        try {
            startOperation();
            Query query = session.createQuery("from " + type);
            objects = (List<T>) query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return objects;
	}
	
	@SuppressWarnings("unchecked")  
    public List<T> findByExample(T exampleInstance, String... excludeProperty) {  
		try{
			Criteria crit = session.createCriteria(type);
	        Example example =  Example.create(exampleInstance);  
	        for (String exclude : excludeProperty) {  
	            example.excludeProperty(exclude);  
	        }  
	        crit.add(example);  
	        return crit.list();
		}catch(HibernateException e){
			handleException(e);
		}finally{
			HibernateFactory.close(session);
		}
		
        return null;  
    }  
}