package com.roh.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.roh.dao.UserDao;

@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	private String username = null;
	
	private String password = null;
	
	@OneToMany(mappedBy="user", cascade= CascadeType.ALL , fetch=FetchType.EAGER)
	List<Image> imageList ;
	
	public User() {}
	public User(String u, String p) {
		this.username = u;
		this.password = p;
	}
	
	public List<Image> getImageList() {
		return imageList;
	}

	public void setImageList(List<Image> imageList) {
		this.imageList = imageList;
	}
	
	public static List<Image> getImageList(String name) {
		UserDao userDao = new UserDao();
		User u = userDao.getUser(name);
		
		List<Image> li;
		try {
			li = u.getImageList();
		} catch (Exception e) {
			li = null ;
		}
		
		return li; 
	} 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
