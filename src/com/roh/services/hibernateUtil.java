package com.roh.services;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class hibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory() {
		
		if(sessionFactory == null) {
			try {
				
				sessionFactory = new Configuration().configure().buildSessionFactory();
                return sessionFactory;
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return sessionFactory;
		
	}

}
