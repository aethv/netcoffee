package com.clinet.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.clinet.model.Account;
import com.clinet.model.Order;
import com.clinet.model.OrderItem;
import com.clinet.model.Product;

public class HibernateUtil {
	
	private static final String HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String HIBERNATE_CONNECTION_DRIVER_CLASS = "hibernate.connection.driver_class";
	private static final String HIBERNATE_CONNECTION_URL = "hibernate.connection.url";
	private static final String HIBERNATE_CONNECTION_USERNAME = "hibernate.connection.username";
	private static final String HIBERNATE_CONNECTION_PASSWORD = "hibernate.connection.password";
	private static final String HIBERNATE_CONNECTION_AUTORECONNECT = "hibernate.connection.autoReconnect";
	private static final String CONNECTION_PROVIDER_CLASS = "connection.provider_class";
	private static final String C3P0_MIN_SIZE = "c3p0.min_size";
	private static final String C3P0_MAX_SIZE = "c3p0.max_size";
	private static final String C3P0_TIMEOUT = "c3p0.timeout";
	private static final String C3P0_MAX_STATEMENTS = "c3p0.max_statements";
	
    private static final SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
        	Configuration config = new Configuration()
	        	.addPackage("com.clinet.model")
        		.addAnnotatedClass(Account.class)
        		.addAnnotatedClass(Order.class)
        		.addAnnotatedClass(OrderItem.class)
        		.addAnnotatedClass(Product.class)
        		
        		.setProperty(HIBERNATE_DIALECT, ResourceUtils.getResourceName(HIBERNATE_DIALECT))
	        	.setProperty(HIBERNATE_HBM2DDL_AUTO, ResourceUtils.getResourceName(HIBERNATE_HBM2DDL_AUTO))
	        	.setProperty(HIBERNATE_SHOW_SQL, ResourceUtils.getResourceName(HIBERNATE_SHOW_SQL))
	        	.setProperty(HIBERNATE_CONNECTION_DRIVER_CLASS, ResourceUtils.getResourceName(HIBERNATE_CONNECTION_DRIVER_CLASS))
	            .setProperty(HIBERNATE_CONNECTION_URL, ResourceUtils.getResourceName(HIBERNATE_CONNECTION_URL))
	            .setProperty(HIBERNATE_CONNECTION_USERNAME, ResourceUtils.getResourceName(HIBERNATE_CONNECTION_USERNAME))
	            .setProperty(HIBERNATE_CONNECTION_PASSWORD, ResourceUtils.getResourceName(HIBERNATE_CONNECTION_PASSWORD))
	            .setProperty(HIBERNATE_CONNECTION_AUTORECONNECT, ResourceUtils.getResourceName(HIBERNATE_CONNECTION_AUTORECONNECT))
	            .setProperty(CONNECTION_PROVIDER_CLASS, ResourceUtils.getResourceName(CONNECTION_PROVIDER_CLASS))
	            .setProperty(C3P0_MIN_SIZE, ResourceUtils.getResourceName(C3P0_MIN_SIZE))
	            .setProperty(C3P0_MAX_SIZE, ResourceUtils.getResourceName(C3P0_MAX_SIZE))
	            .setProperty(C3P0_TIMEOUT, ResourceUtils.getResourceName(C3P0_TIMEOUT))
	            .setProperty(C3P0_MAX_STATEMENTS, ResourceUtils.getResourceName(C3P0_MAX_STATEMENTS))
//	            .setProperty("hibernate.current_session_context_class", "jta")
//	            .setProperty("hibernate.transaction.factory_class", "org.hibernate.transaction.JTATransactionFactory")
	            ;
        		
        	
        	ServiceRegistry registry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
            sessionFactory = config.buildSessionFactory(registry);
            
        } catch (Throwable ex) {
        	System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
