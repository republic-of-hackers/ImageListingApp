<%@page import="java.io.FileOutputStream"%>
<%@page import="com.roh.model.Image"%>
<%@page import="com.roh.model.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 
<%
Cookie cookies [] = request.getCookies() ;
if (cookies==null)
{
	response.sendRedirect("login.jsp");
	return ;
}
String userName = null ;
for (Cookie c : cookies)
{
	String name = c.getName();
	String value = c.getValue();
	
	if(name.equals("username")) {
		userName = value;
		System.out.println(" : " + userName);
		break ;
	}
	
}
if (userName==null)
{
	response.sendRedirect("login.jsp");
	return ;
}
%> 
   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Image Uploader App</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
body{
display: inline;
margin: 0px ;
}
table,th,td{
  border:1px solid black ;
  border-collapse:collapse ;
  text-align: center ;
}
img{
margin:5px;
}
#hiddenDiv{
position: fixed;
display:none ;
top:33% ;
left:45% ;
background-color: white ;
}
#overlay{
width: 100% ;
height: 100% ;
background-color: grey ;
opacity:0.7 ;
position: fixed;
display:none ;
}
legend {
     margin: auto;
}

</style>

<script type="text/javascript">

function getImageNamePromptMessage() {
	let name = prompt("Enter New Name: ");
	document.getElementById("imgname").value = name;
}

</script>
</head>
<body>

<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="<%=request.getContextPath()%>">Image Uploader App</a>
    </div>
    <ul class="nav navbar-nav">
    	
    	<li class="active"><a href="#">Login User: <%= userName %></a></li>
      <li class="active"><a href="<%=request.getContextPath()%>/logout">Logout</a></li>
    </ul>
  </div>
</nav>
<hr>
<center><h3> Image Utility Management</h3></center>
<hr>
<br>

<form action="<%=request.getContextPath()%>/uploadimage" method="POST" enctype = "multipart/form-data">
<label>Upload Image File: </label> &nbsp;&nbsp;
<input type="file" name="imgFile"> &nbsp;&nbsp;
<button type="submit"> Upload </button>
</form>

<!-- uplaoded images -->

<div>
<h2 style="margin-top: 40px">Uploaded Images</h2>
<table style="width: 100% ">
<tr>
<th>S.NO</th>
<th>Name</th>
<th>Size</th>
<th>Preview</th>
<th>Action</th>
</tr>

<% 
List<Image> li = null;
try {
 	li =  User.getImageList(userName);
} catch (Exception ex){
	ex.printStackTrace();	
}

String id, name, size, preview , action ,imgPath ;

String SAVE_DIR,appPath,savePath,filePath ;
SAVE_DIR = "Images";
appPath = request.getServletContext().getRealPath("");
savePath = appPath + SAVE_DIR;

FileOutputStream fos ;
String action1 = "<form action='" + request.getContextPath() 
	   + "/deleteimage' method='post'>"
	   + "<input type='hidden' name='imgid' value='%s'/>"
	   + "<button type='submit'> X </button></form>";

String action2 = "<form action='" + request.getContextPath() 
		+ "/updateimage' method='post'>"
		+ "<input type='hidden' name='imgid' value='%s'/>"
		+ "<input type='hidden' id='imgname' name='newname' value='default'/>"
		+ "<button type='submit' onclick=\"getImageNamePromptMessage()\")> E </button></form>";
		
for(Image i : li){
	id = i.getId()+"";
	name = i.getImgName();
	size = i.getImgSize()+" Bytes";
	
	//filePath = savePath +"\\" + name + "_" + id;
	filePath = savePath +"\\" + id + "_" + name;
	
	System.out.println("filepath in jsp: " + filePath);
	fos = new FileOutputStream(filePath);
    fos.write(i.getImageData());
    
	/* preview = "Images\\"+ name + "_" + id;
	preview = "Images\\"+ name + "_" + id; */
	
	out.println("<tr>");
	out.println("<td>"+id+"</td>");
	out.println("<td class='ImgName'>"+name+"</td>");
	out.println("<td>"+size+"</td>");
	out.println("<td><img width='150px' height = '150px' src = '" + filePath + "'></td>");
	out.println("<td>"+String.format(action1, id)  + String.format(action2, id)+"</td>");
	out.println("</tr>");	
}
%>
</table>
</div>

</body>
</html>