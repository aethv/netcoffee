package com.clinet.utils;

import com.clinet.dao.AccountDAO;
import com.clinet.model.Account;
import com.clinet.model.AccountDTO;

public class Test {
    
    public static void main(String[] args) {
//		Session session = HibernateUtil.getSessionFactory().openSession();
//		session.beginTransaction();
//		
//		Account acc = (Account) session.get(Account.class, 1L);
//		System.out.println(acc);
    	AccountDAO acc = new AccountDAO();
    	AccountDTO dto = new AccountDTO("abc", "def");
    	Account a = acc.validateLogin(dto);
    	
    	
    	if(a != null){
    		System.out.println("user not exist");
    	}
    	
	}
}
