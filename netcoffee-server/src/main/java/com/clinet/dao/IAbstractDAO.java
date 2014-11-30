package com.clinet.dao;

import java.util.List;

public interface IAbstractDAO<T> {
	void insert(T t);
	
	void insert(List<T> t);

	void delete(T t);
	
	void delete(List<T> lst);

	void update(T t);

	void update(List<T> lst);
	
	T findOne(Long id);

	List<T> findAll();
	
//	long count();

//	List<T> findByPropertyName(String propName, Object value);
//
//	T getUniqueByPropertyName(String propName, Object value);
//
	List<T> findByExample(T exampleInstance, String...excludeProperty);

	
}
