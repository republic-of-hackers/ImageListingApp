package com.roh.dao;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.roh.model.User;
import com.roh.services.hibernateUtil;


public class UserDao {
	
	
	public User getUser(String name) {
		
		User user = null;
		Session session = hibernateUtil.getSessionFactory().openSession();
		try {
			
			session.beginTransaction();
			Query query = session.createQuery("from User where userName = :param");
			query.setParameter("param", name);
			try {
				user = (User) query.getSingleResult();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			session.getTransaction().commit();
			session.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return null;
		}
		
		return user;
		
	}
	
	public boolean validate(String username, String password) {
		
		Transaction transaction = null;
		User user = null;
	
		try {
			
			Session session = hibernateUtil.getSessionFactory().openSession();
			
			transaction = session.beginTransaction();
			
			user = (User) session.createQuery("FROM User U WHERE U.username = :username")
					.setParameter("username", username)
					.getSingleResult();
			
			if(user != null && user.getPassword().equals(password)) {
				return true;
			}
			
			transaction.commit();
			
		} catch ( Exception ex) {
			if(transaction != null) {
				transaction.rollback();
			}
			ex.printStackTrace();
			
		}
		return false;
	}

}
