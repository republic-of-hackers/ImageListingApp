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

@WebServlet("/updateimage")
public class ImageUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ImageDao imgDao;
	AlertMessages alm;
	
	public void init() {
		imgDao = new ImageDao();
		alm = new AlertMessages();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("ImageUploader.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int imgID = Integer.parseInt(req.getParameter("imgid"));
		String imgname = req.getParameter("newname");
		if(imgDao.editImage(imgID, imgname)) {
			RequestDispatcher dispatcher = req.getRequestDispatcher("ImageUploader.jsp");
			dispatcher.forward(req, resp);
		} else {
			alm.getAlertOutputMessage("Image is not deleted. Redirecting to Login", "/ImageUploaderApp/logout.jsp");
		}
	}
	
	

}
