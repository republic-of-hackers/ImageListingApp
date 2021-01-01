package com.roh.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.roh.constants.AlertMessages;
import com.roh.dao.UserDao;


@WebServlet("/login")
public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UserDao loginDao;
	private AlertMessages alm;
	
	public void init() {
		loginDao = new UserDao();
		alm = new AlertMessages();
	}
	
	private void authenticate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(loginDao.validate(username, password)) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("ImageUploader.jsp");
			response.addCookie(new Cookie("username", username));
			response.addCookie(new Cookie("password", password));
			dispatcher.forward(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.println(alm.getAlertOutputMessage("Login First!!", "/ImageUploaderApp/login"));
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			authenticate(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
