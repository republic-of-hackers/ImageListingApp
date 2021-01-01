package com.roh.dao;

import org.hibernate.Session;

import com.roh.model.Image;
import com.roh.model.User;
import com.roh.services.hibernateUtil;

public class ImageDao {
	
	public boolean saveImage(Image i, String username) {
		
		Session session = hibernateUtil.getSessionFactory().openSession();
		
		try {
			
			session.beginTransaction();
			
			User user = (User) session.createQuery("from User where username=:username")
					.setParameter("username", username)
					.getSingleResult();
			
			user.getImageList().add(i);
			i.setUser(user);
			
			session.save(user);
			session.getTransaction().commit();
			session.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return false;
		}
		return true;
	}
	
	public boolean editImage(int id, String name) {
		
		Session session = hibernateUtil.getSessionFactory().openSession();
		
		try {
			session.beginTransaction();
			
			Image i = (Image) session.get(Image.class, id);
			i.setImgName(name);
			session.update(i);
			
			session.getTransaction().commit();
			session.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return false;
		}
		
		return true;
	}
	
	public boolean deleteImage(int id) {
		
		Session session = hibernateUtil.getSessionFactory().openSession();
		
		try {
			session.beginTransaction();
			Image i = (Image) session.get(Image.class, id);
			User u = i.getUser();
			u.getImageList().remove(i);
			session.delete(i);
			session.getTransaction().commit();
			session.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return false;
		}
		
		return true;
	}

}
