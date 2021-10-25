<%@page import="com.java.oyo.Room"%>
<%@page import="com.java.oyo.Status"%>
<%@ page import="com.java.oyo.MainProg" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>	
	<%
	Room room = new Room();
	room.setType(request.getParameter("type"));
	room.setStatus(Status.valueOf(request.getParameter("status")));
	room.setCostperday(Integer.parseInt(request.getParameter("cost")));

		MainProg obj=new MainProg();
		String res = obj.addroom(room); 
		out.println(res); 
	%>	
</body>
</html>