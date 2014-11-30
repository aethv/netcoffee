package com.clinet.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.clinet.utils.HibernateUtil;

public class HibernateFactory {
	private static SessionFactory sessionFactory;
	private static Logger log = Logger.getLogger(HibernateFactory.class);

	/**
	 * Constructs a new Singleton SessionFactory
	 * 
	 * @return
	 * @throws HibernateException
	 */
	public static SessionFactory buildSessionFactory()
			throws HibernateException {
		if (sessionFactory != null) {
			closeFactory();
		}
		return HibernateUtil.getSessionFactory();
	}

	/**
	 * Builds a SessionFactory, if it hasn't been already.
	 */
	public static SessionFactory buildIfNeeded()
			throws DataAccessLayerException {
		if (sessionFactory != null) {
			return sessionFactory;
		}
		try {
			return HibernateUtil.getSessionFactory();
		} catch (HibernateException e) {
			throw new DataAccessLayerException(e);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session openSession() throws HibernateException {
		sessionFactory = buildIfNeeded();
		return sessionFactory.openSession();
	}

	public static void closeFactory() {
		if (sessionFactory != null) {
			try {
//				sessionFactory.close();
			} catch (HibernateException ignored) {
				log.error("Couldn't close SessionFactory", ignored);
			}
		}
	}

	public static void close(Session session) {
		if (session != null) {
			try {
				session.close();
			} catch (HibernateException ignored) {
				log.error("Couldn't close Session", ignored);
			}
		}
	}

	public static void rollback(Transaction tx) {
		try {
			if (tx != null) {
				tx.rollback();
			}
		} catch (HibernateException ignored) {
			log.error("Couldn't rollback Transaction", ignored);
		}
	}
}