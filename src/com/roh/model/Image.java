package com.roh.model;

import java.io.File;

import javax.persistence.*;

@Entity
@Table(name="images")
public class Image {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id ;
	
	@Transient
	private File file ;
	
	@Column(name="name")
	String imgName ;
	
	@Column(name="size")
	long imgSize ;
	
	@Column(name="img_data")
	@Lob
	private byte[] imageData;
	
	@ManyToOne
	User user ;
	
	public void setImg(File file) {
		this.file = file;
		this.imgSize =  file.length();
		this.imgName = file.getName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public long getImgSize() {
		return imgSize;
	}

	public void setImgSize(long imgSize) {
		this.imgSize = imgSize;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
