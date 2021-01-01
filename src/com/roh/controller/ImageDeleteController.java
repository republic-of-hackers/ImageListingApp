package com.roh.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.roh.constants.AlertMessages;
import com.roh.dao.ImageDao;


@WebServlet("/deleteimage")
public class ImageDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ImageDao imgDao;
	AlertMessages alm;
	
	public void init() {
		imgDao = new ImageDao();
		alm = new AlertMessages();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/ImageUploaderApp/ImageUploader.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int imgID = Integer.parseInt(request.getParameter("imgid"));
		if(imgDao.deleteImage(imgID)) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("ImageUploader.jsp");
			dispatcher.forward(request, response);
		} else {
			alm.getAlertOutputMessage("Image is not deleted. Redirecting to Login", "/ImageUploaderApp/logout.jsp");
		}
	}
}
