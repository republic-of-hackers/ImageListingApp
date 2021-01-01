package com.roh.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.roh.constants.AlertMessages;
import com.roh.dao.ImageDao;
import com.roh.model.Image;

@WebServlet("/uploadimage")
@MultipartConfig(maxFileSize=1000*1024)

public class ImageUploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ImageDao imgDao;
	AlertMessages alm;
	
	public void init() {
		imgDao =  new ImageDao();
		alm = new AlertMessages();
	}
	
	private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/ImageUploaderApp/uploadimage");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		String username  = null;
		
		Cookie[] cookies = request.getCookies();
		if (cookies!=null)
		{
			for (Cookie c : cookies) {
				if(c.getName().equals("username"))
					username = c.getValue();
			}
		}
		
		if (username==null)
			out.println(alm.getAlertOutputMessage("Login First!!", "/ImageUploaderApp/login"));
		else {
			Part part = null ;
			try {
				part = request.getPart("imgFile");
			} catch (Exception e) {
				out.println(alm.getAlertOutputMessage("Image size greater than 1000 KB!!", "/ImageUploaderApp/ImageUploader.jsp"));
				return  ;
			}
			
			String fileName = extractFileName(part);
			
			String SAVE_DIR = "Images";
			String appPath = request.getServletContext().getRealPath("");
			String savePath = appPath + SAVE_DIR;
			String filePath = savePath + File.separator + fileName;
			
			InputStream inputStream = part.getInputStream();
			byte[] bFile = new byte[(int) part.getSize()];
			inputStream.read(bFile);
			part.write(filePath);
			
			Image i = new Image() ;
			i.setImg(new File(filePath)) ;
			i.setImageData(bFile);
			
			if(imgDao.saveImage(i, username))
				out.println(alm.getAlertOutputMessage("Image is saved, Successfully", "ImageUploader.jsp"));
			else
				out.println(alm.getAlertOutputMessage("Image is not saved, Failed", "ImageUploader.jsp"));
		}
	}
}
