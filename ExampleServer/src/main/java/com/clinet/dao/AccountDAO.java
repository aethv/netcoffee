package com.clinet.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;

import com.clinet.model.Account;
import com.clinet.model.AccountDTO;

public class AccountDAO extends AbstractDao<Account>{

	private static final Logger log = Logger.getLogger(AccountDAO.class);
	
	public AccountDAO() {
		super(Account.class);
	}
	
	public Account validateLogin(AccountDTO dto){
		Account acc = null;
		try{
			startOperation();
			Query query = session.getNamedQuery("Account.validate").setParameter("acc", dto.getUsername()).setParameter("pwd", dto.getPassword());
			acc = (Account) query.uniqueResult();
			tx.commit();
		}catch(Exception ex){
			log.error(ex.getMessage(), ex);
		}
		return acc;
	}

}