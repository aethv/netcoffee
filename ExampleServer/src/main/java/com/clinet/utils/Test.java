package com.clinet.utils;
import org.hibernate.Session;

import com.clinet.model.Account;
import com.clinet.utils.HibernateUtil;


public class Test {
    
    public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		Account acc = (Account) session.get(Account.class, 1L);
		System.out.println(acc);
		
	}
}
