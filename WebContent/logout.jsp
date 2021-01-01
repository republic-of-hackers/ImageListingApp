<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
	Cookie cookies[] = request.getCookies();
	if(cookies != null){
		for(Cookie c: cookies){
			c.setMaxAge(0);
			response.addCookie(c);
		}
	}
	response.sendRedirect("login.jsp");
%>