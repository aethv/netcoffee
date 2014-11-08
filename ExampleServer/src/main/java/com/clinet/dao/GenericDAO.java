package com.clinet.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.clinet.utils.HibernateUtil;

public abstract class GenericDAO<T extends Serializable> implements
		IGenericDAO<T> {

	protected Session session = null;
	protected Transaction transaction = null;
	T t;

	public GenericDAO(T t) {
		this.t = t;
	}

	/**
	 * This method is responsible to open the sesseio and begin the transaction.
	 * If I don't use it, we'll need write this lines in all methods
	 * 
	 * 
	 * @author Luiz Segundo
	 */
	public void beginSession() {
		if (session == null || !session.isOpen()) {
			session = HibernateUtil.getSessionFactory().openSession();
		}

	}

	/**
	 * 
	 * This method is responsable for begin the transaction of the DB. If I
	 * don't use it, we'll need write this lines in all methods
	 * 
	 * @author Luiz Segundo
	 */
	public void beginTransaction() {
		if (transaction == null || !transaction.isActive()) {
			transaction = session.beginTransaction();
		}

	}

	/**
	 * 
	 * This method is responsable for finish all connections and transactions
	 * with the DB. If I don't use it, we'll need write this lines in all
	 * methods
	 * 
	 * @author Luiz Segundo
	 */
	public void finalizeHibernate() {
		if (((transaction != null) && (session != null))) {
			if (transaction.isActive() && session.isOpen()) {
				transaction.commit();
				session.close();
			}
		}
	}

	/**
	 * 
	 * This method is responsable for add any object at the DB.
	 * 
	 * 
	 * @param t
	 *            any object
	 * @return true if the "insert" was done correct.
	 * @throws Exception
	 *             for any problem and the "catch" do a rollback in the DB.
	 */

	public boolean addObject(T t) throws Exception {
		try {
			beginSession();
			beginTransaction();
			session.merge(t);
		} catch (Exception e) {
			transaction.rollback();
			throw new Exception(
					"Some error has occurred in add metod on 'addObject'");
		} finally {
			finalizeHibernate();
		}
		return true;

	}

	/**
	 * 
	 * This method is responsable for remove any object at the DB.
	 * 
	 * 
	 * @param t
	 *            any object
	 * @return true if the "delete" was done correct.
	 * @throws Exception
	 *             for any problem and the "catch" do a rollback in the DB.
	 */

	public boolean removeObject(T t) throws Exception {
		try {
			beginSession();
			beginTransaction();
			session.delete(t);

		} catch (Exception e) {
			transaction.rollback();
			throw new Exception(
					"Some error has occurred in add metod on 'removeObject'");
		} finally {
			finalizeHibernate();
		}
		return true;
	}

	/**
	 * 
	 * This method is responsable for "update" any object at the DB.
	 * 
	 * 
	 * @param t
	 *            any object
	 * @return true if the "update" was done correct.
	 * @throws Exception
	 *             for any problem and the "catch" do a rollback in the DB.
	 */

	public boolean updateObject(T t) throws Exception {
		try {
			beginSession();
			beginTransaction();

			session.merge(t);

		} catch (Exception e) {
			transaction.rollback();
			throw new Exception(
					"Some error has occurred in add metod on 'updateObject'");
		} finally {
			finalizeHibernate();
		}
		return true;
	}

	/**
	 * 
	 * Search a object for name and last name.
	 * 
	 * @param name
	 * @param lastName
	 * @param t
	 * @return a object for the attribute name.
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public T searchObjectForNameLastName(String name, String lastName)
			throws Exception {
		try {
			this.beginSession();
			this.beginTransaction();
			Criteria c = session.createCriteria(t.getClass());
			c.add(Restrictions.ilike("nome", name));
			c.add(Restrictions.ilike("sobrenome", lastName));
			t = (T) c.uniqueResult();
			return t;
		} catch (Exception e) {
			transaction.rollback();
			throw new Exception("Erro ao tentar buscar por Nome" + t.getClass());
		} finally {
			this.finalizeHibernate();
		}
	}

	/**
	 * 
	 * Search a object for name .
	 * 
	 * @param name
	 * @param lastName
	 * @param t
	 * @return a object for the attribute name.
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public T searchObjectForName(String name) throws Exception {
		try {
			this.beginSession();
			this.beginTransaction();
			Criteria c = session.createCriteria(t.getClass());
			c.add(Restrictions.ilike("primeiroNome", name));
			t = (T) c.uniqueResult();
			return t;
		} catch (Exception e) {
			transaction.rollback();
			throw new Exception("Erro ao tentar buscar por Nome" + t.getClass());
		} finally {
			this.finalizeHibernate();
		}
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * @param atributo
	 * @param busca
	 * @param t
	 * @return A list of object for the any attribute
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<T> searchForAttributeString(String attribute, String search)
			throws Exception {
		try {
			this.beginSession();
			this.beginTransaction();
			Criteria c = session.createCriteria(t.getClass());
			c.add(Restrictions.ilike(attribute, "%" + search + "%"));
			if (c.list() == null) {
				throw new Exception("Não foi encontrado");
			}
			return c.list();
		} catch (Exception e) {
			transaction.rollback();
			throw new Exception("Erro ao tentar buscar Lista de " + attribute
					+ t.getClass());
		} finally {
			this.finalizeHibernate();
		}
	}

	@SuppressWarnings("unchecked")
	public T searchObjectForID(Long id) throws Exception {
		try {
			this.beginSession();
			this.beginTransaction();
			Criteria c = session.createCriteria(t.getClass());
			c.add(Restrictions.ilike("id", id));
			if (c.list() == null) {
				throw new Exception("Não foi encontrado");
			}
			return (T) c.uniqueResult();
		} catch (Exception e) {
			transaction.rollback();
			throw new Exception("Erro ao tentar buscar objeto" + t.getClass());
		} finally {
			this.finalizeHibernate();
		}
	}

}